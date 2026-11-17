# Technical Specification: Better Action Chrome Extension

**Better Action** is a developer-focused productivity Chrome Extension that replaces the rigid, 5-element constraint of the native GitHub Actions `workflow_dispatch` web form with an advanced, developer-friendly interface in your **browser sidebar**. Forms are defined via SurveyJS JSON files in your repository and rendered using Preact & SurveyJS.

## Features

- **Lives in Chrome Browser Sidebar:** Uses the Manifest V3 Side Panel API (`chrome.sidePanel`), seamlessly available alongside GitHub without interfering with page DOM.
- **GitHub OAuth Login:** Native OAuth 2.0 Device Flow authorization so users can log in directly without having to copy-paste tokens. (PAT fallback also supported).
- **Two-Page Sidebar Experience:**
  - **Page 1 (Workflows & Jobs):** When viewing GitHub Actions pages (`/:owner/:repo/actions[/*]`), displays all workflows and recent runs with real-time quick search.
  - **Page 2 (Advanced Form View):** Clicking "Run workflow" transitions the sidebar into the SurveyJS dynamic form interface for interactive parameter entry and dispatching.
- **Standalone `dist/` Build:** Self-contained Chrome Extension built into the `dist/` directory ready for "Load unpacked".

## Tech-Stack
Preact, survey-core, survey-js-ui, Vite, esbuild

---

## 1. Safe Configuration Strategy

To prevent GitHub from rejecting your workflow files, this architecture exploits a native feature of GitHub's YAML parser: **GitHub ignores custom properties it doesn't understand.**

All advanced form layouts are defined in a standalone SurveyJS JSON file (e.g., `.github/better_action/sample_form.json`) and referenced by the `better_action_form` input's `default` value.

### Production-Ready Workflow Template
```yaml
name: Production Deployment

# 1. Official GitHub triggers remain untouched and perfectly valid
on:
  workflow_dispatch:
    inputs:
      deploy_notes:
        type: string
        description: "Release notes (Fallback text box for standard GitHub UI)"
      target_microservices:
        type: string
        description: "Comma-separated services (Fallback text box for standard GitHub UI)"
      feature_flags:
        type: string
        description: "Selected flags (Fallback text box for standard GitHub UI)"

# 2. Custom input safely ignored by GitHub, parsed by your extension
      better_action_form:
        type: string
        required: false
        default: .github/better_action/sample_form.json
```

### SurveyJS Form Definition (`sample_form.json`)
```json
{
  "showQuestionNumbers": false,
  "completeText": "Dispatch Workflow ⚡",
  "pages": [{
    "elements": [
      {
        "type": "comment",
        "name": "deploy_notes_custom",
        "title": "Deploy Notes",
        "placeholder": "Enter markdown release logs here...",
        "rows": 6
      },
      {
        "type": "tagbox",
        "name": "target_microservices_custom",
        "title": "Target Microservices",
        "choicesByUrl": {
          "url": "https://company.com",
          "path": "data.services",
          "valueName": "service_id",
          "titleName": "display_name"
        },
        "fetchConfig": {
          "method": "GET",
          "headers": {
            "Authorization": "Bearer ${SETTINGS_INTERNAL_TOKEN}"
          }
        }
      },
      {
        "type": "tagbox",
        "name": "feature_flags_custom",
        "title": "Feature Flags",
        "choices": [
          "beta-dashboard",
          "new-payment-gateway",
          "performance-logs"
        ]
      }
    ]
  }]
}
```

---

## 2. Architecture & Sidebar Flow

```
[ GitHub Actions UI ] --(Detects active repo & branch)--> [ Content Script ]
                                                                 │
                                                       (Opens side panel)
                                                                 ▼
[ GitHub REST API ] <--(Dispatches via API)-- [ Chrome Browser Sidebar ]
                                                 ├─ Page 1: Jobs & Workflows List (Search)
                                                 └─ Page 2: Advanced SurveyJS Form View
                                                                 ▲
                                                                 │
                                                 [ OAuth Vault / Local Storage ]
```

### Operational Lifecycle
1. **Sidebar Activation:** Clicking the extension toolbar icon opens the browser's native right sidebar (`chrome.sidePanel`).
2. **Page 1 - Workflows & Jobs:** Sidebar connects with active tab, fetches repository workflows and runs via GitHub API, and renders them with a top search bar for quick filtering.
3. **Trigger Transition:** Clicking "⚡ Run workflow" on any card (or GitHub's native Run Workflow button) transitions the sidebar to **Page 2**.
4. **Page 2 - Form Construction:** Sidebar fetches the YAML, reads `better_action_form`'s default path, loads the SurveyJS definition, resolves dynamic datasources, and renders interactive inputs.
5. **Submission:** User fills out parameters and submits; extension maps inputs and dispatches via GitHub API `POST /repos/{owner}/{repo}/actions/workflows/{workflowId}/dispatches`.

---

## 3. Building the Extension

Run the build command:
```bash
npm run build
```

This generates the complete, self-contained Chrome Extension in the `dist/` folder:
- `dist/manifest.json`: Manifest V3 configuration
- `dist/sidepanel/`: Browser sidebar HTML, Preact bundle, and CSS
- `dist/background/`: Background service worker with OAuth & API proxies
- `dist/content/`: Content script with page hooks
- `dist/icons/`: Generated icons in all required sizes (16, 32, 48, 128)
- `dist/settings/`: Options & secrets management page

To load into Chrome:
1. Open `chrome://extensions/`
2. Enable "Developer mode" (top right)
3. Click "Load unpacked" and select the `better_action/dist` folder.
