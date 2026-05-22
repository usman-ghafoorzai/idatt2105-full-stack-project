# Security Review

## Context
This repository appears to be a university full-stack project intended for coursework and demonstration.  
It should be treated as a learning artifact, not a production system.

This review is documentation-only and based on repository analysis of code, configuration, and project structure.

## Scope and Position
- The findings below describe security and privacy considerations for a **public repository context**.
- The project can still be suitable as a portfolio/demo with clear limitations.
- The recommendations are forward-looking hardening steps, not a claim that the project is production-ready.

---

## 1) Hardcoded JWT Secret / Demo Configuration
**Description**  
The backend configuration includes a static JWT secret in versioned properties and uses demo-oriented profile settings by default.

**Potential Risk**  
If reused outside a classroom/demo context, static in-repo secrets increase the chance of token forgery risk and weak environment separation.

**Recommended Improvement**  
- Move secrets to environment variables or secret management.
- Keep demo values only in non-sensitive sample templates.
- Use explicit profile separation for local demo vs. production-like runs.

**Acceptable for a demo/university project?**  
**Partially acceptable** for coursework with mock/demo data, but **not acceptable** for any production-like deployment.

---

## 2) User/Password Fields in API Responses
**Description**  
User entities are returned directly in some API responses, and user models include a password field.

**Potential Risk**  
Even when hashed, password fields should not be exposed in API payloads. This also increases accidental data leakage risk.

**Recommended Improvement**  
- Return dedicated response DTOs that exclude password fields.
- Add explicit serialization safeguards (for example, non-serializing sensitive fields).
- Limit user response payloads to minimum necessary attributes.

**Acceptable for a demo/university project?**  
**Weakly acceptable** only for tightly controlled demo use with non-real accounts; still a high-priority improvement for portfolio quality.

---

## 3) Broad Public `GET /api/**` Access
**Description**  
Security rules allow broad unauthenticated read access across `GET` endpoints under `/api/**`.

**Potential Risk**  
This can expose endpoints and data that should be authenticated (for example user-related reads), depending on controller behavior.

**Recommended Improvement**  
- Apply allow/deny rules per endpoint group instead of blanket `GET` access.
- Keep only intentionally public catalog/search endpoints open.
- Require authentication for user/account-specific data.

**Acceptable for a demo/university project?**  
**Conditionally acceptable** for a read-heavy demo, but **not acceptable** if any personal or account-linked data is included.

---

## 4) Weak or Incomplete Authorization Boundaries
**Description**  
Some authorization boundaries appear incomplete (for example commented role checks and ID-based operations without clear ownership enforcement).

**Potential Risk**  
Authenticated users may perform actions beyond intended permissions (role escalation or cross-user operations).

**Recommended Improvement**  
- Enforce role-based checks for admin operations.
- Enforce ownership checks (authenticated principal must match resource owner unless admin).
- Add authorization-focused integration tests.

**Acceptable for a demo/university project?**  
**Partially acceptable** in a trusted classroom setting, but **not acceptable** for public multi-user deployment.

---

## 5) Demo Seed Users and Credentials
**Description**  
The project includes seeded demo users and documented demo login credentials for ease of testing.

**Potential Risk**  
If the same credentials are reused in shared or externally reachable environments, unauthorized access risk increases.

**Recommended Improvement**  
- Keep seed users clearly marked as demo-only.
- Ensure demo credentials are never reused in non-demo environments.
- Prefer one-time initialization and environment-specific test data policies.

**Acceptable for a demo/university project?**  
**Generally acceptable** for coursework demos when clearly documented and isolated from real environments.

---

## 6) Token Storage in `sessionStorage`
**Description**  
The frontend stores JWT tokens in browser `sessionStorage`.

**Potential Risk**  
Tokens in web storage can be exposed in XSS scenarios, even though `sessionStorage` is shorter-lived than persistent storage.

**Recommended Improvement**  
- For stronger security, use HttpOnly cookies with secure flags and CSRF protections.
- Add CSP and output encoding hardening to reduce XSS exposure.
- Keep token lifetime short and scope minimal.

**Acceptable for a demo/university project?**  
**Common and acceptable** for many student demos, but should be upgraded for production-grade security.

---

## 7) Hardcoded Backend URL in Frontend
**Description**  
The frontend API client uses a fixed localhost backend URL.

**Potential Risk**  
This reduces environment flexibility and can cause deployment mistakes or accidental misconfiguration.

**Recommended Improvement**  
- Move API base URL to environment-driven configuration (`.env` per environment).
- Document expected environment variables for local/demo/CI runs.

**Acceptable for a demo/university project?**  
**Acceptable** for local development demos; not ideal for reusable deployment workflows.

---

## 8) Third-Party Reverse Geocoding Privacy Consideration
**Description**  
Reverse geocoding requests are sent to a third-party service with latitude/longitude data.

**Potential Risk**  
Location-related data may be shared with an external provider, creating privacy/compliance concerns depending on data sensitivity.

**Recommended Improvement**  
- Document third-party data flow and privacy implications.
- Use synthetic/non-sensitive location data for demos.
- Consider a backend proxy or privacy-preserving approach if real user data is involved.

**Acceptable for a demo/university project?**  
**Generally acceptable** for non-sensitive demo data with clear disclosure.

---

## Overall Assessment
For a university portfolio project, the current security posture is understandable and typical of a demo-first implementation.  
For any production-like usage, the issues above should be addressed before public deployment or real-user data handling.
