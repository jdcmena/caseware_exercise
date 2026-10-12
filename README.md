# Pending Template Update Detection

Take-home design exercise: let users see at a glance which engagement files have a pending product template update, and show a summary of the incoming changes so they can apply or decline.

## Repository contents

| Path | What it is |
|---|---|
| `resources/documents/Template Update Design.md` | Design doc: architecture, implementation plan, testing, observability, failure modes and tradeoffs |
| `resources/documents/system-Design.png` | System design diagram |
| `resources/documents/entity-model.png` | Entity model: `product_template` (shared); `engagement_file`, `engagement_update`, `audit` (per firm) |
| `src/` | Part 2: a small Java slice (see below) |

## Design in brief

- A template publish emits an event (SNS). A summarizer asks the engagement service which template versions are in use (versions and counts only, no firm IDs), then queues one diff job per version in use (`from -> latest`).
- Diff workers write the diff and summary to S3 at a deterministic key, then call `POST /engagement/v1/updates`, a monotonic upsert that opens or advances one pending row per engagement.
- The list view reads pending rows by `(firm_id, status)` and never touches the engagement store. The summary loads from S3 on the detail view.

See `resources/documents/Template Update Design.md` for the full reasoning and tradeoffs.

## Part 2: implemented slice

The slice covers the "at a glance" requirement: listing a firm's pending engagement updates.

- `EngagementUpdateRepository.getPendingByFirm(firmId)` is the storage port.
- `EngagementUpdateService.listPendingEngagementUpdates(firmId)` returns the firm's pending updates.
- Tests cover firm scoping, the empty case, single repository call, rows returned unchanged.
