# AI usage on this exercise

The following LLMs were used in this exercise:

- Anthropic Claude (free tier)
- Qwen 3.5 4B

In general terms, AI was used as a guide during this exercise to evaluate the coherence of the system design, the ER diagram and the sample code against the problem, and how accurate some concepts were applied in the design.

Anthropic Claude was used to specifically evaluate how the problem statement was being resolved from the data model and the high level design, as well as the trade-offs in place given the time constraints of the exercise, and the additional time used to personally understand and refresh concepts of AWS serverless. Claude was also used to generate a very rough skeleton of the code (enough to get the file structure in place). Finally, it was also used to generate the design document after all the information has been collected.

Qwen 3.5 4B was used to help generate very specific parts of the code (specially tests) as this could be time consuming specially for larger code bases and/or test surfaces. This model was also used to overcome the "free" tier from Anthropic, as many iterations of code generation can burn tokens quickly, where these can be allocated to more demanding tasks.

In all cases, AI results were challenged to review or double check if the output made sense vs the concept or code that was being implemented, as even the latest models are subject to hallucinations despite the recent advances in LLM models.