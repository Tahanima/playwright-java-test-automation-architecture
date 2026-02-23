# Playwright Locator Healing Logic (with MCP)

## 1. Diagnose via MCP
When using the `@playwright` tool:
- Use `playwright_click` with a generic selector to see if the element responds.
- Use the tool's output to find **Aria Roles** and **Labels** that aren't visible in the raw source code.

## 2. Healing Strategy (Java SDK mapping)
Refactor using the live data from MCP into these Java patterns:

| MCP Finding | Java SDK Implementation |
| :--- | :--- |
| Button with name "Submit" | `page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"))` |
| Input with label "Email" | `page.getByLabel("Email")` |
| Image with alt "Logo" | `page.getByAltText("Logo")` |

## 3. The "Double-Check" Rule
Before finalizing the response, the agent should mentally (or via MCP) verify that the proposed locator is unique on the page to avoid "strict mode" violations in Playwright Java.
