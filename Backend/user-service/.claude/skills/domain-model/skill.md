---
name: domain-model
description: Grilling session that challenges your plan against the existing domain model, sharpens terminology, and updates documentation (CONTEXT.md, ADRs) inline as decisions crystallise. Use when user wants to stress-test a plan against their project's language and documented decisions.
disable-model-invocation: true
---
Interview me relentlessly about every aspect of this plan until we reach a shared understanding. Walk down each branch of the design tree, resolving dependencies between decisions one-by-one. For each question, provide your recommended answer.
Ask the questions one at a time, waiting for feedback on each question before continuing.
If a question can be answered by exploring the codebase, explore the codebase instead.
## Domain awareness
During codebase exploration, also look for existing documentation:
### File structure
Most repos have a single context:
```

/
CONTEXT.md
docs/
Ladr/
0001-event-sourced-orders.md
0002-postgres-for-write-model.md
src/
```
If a CONTEXT-MAP.md exists at the root, the repo has multiple contexts. The map points to where each one Lives:
```
/
CONTEXT-MAP.md
docs/
Ladr/
-system-wide decisions
src/
ordering/
CONTEXT.md
docs/adr/
- context-specific decisions
billing/
CONTEXT.md
docs/adr/
```
Create files lazily only when you have something to write. If no CONTEXT.md exists, create one when the first term is resolved. If no docs/adr/ exists, create it when the first ADR is needed.