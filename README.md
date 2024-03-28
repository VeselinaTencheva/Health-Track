# Health-Track
# Health-Track is an electronic system designed to manage and track health records efficiently. This system centralizes records for patients, visitations, physicians, sick leaves, treatments, and diagnoses, facilitating better health management and accessibility.

# Features
 * Role-Based Access Control: Implements four user roles - Patient, Physician, General Practitioner (GP), and Admin, each with distinct access levels.
 * Dynamic Physician Registration: Physicians can register independently or be created by an admin. Physicians with a General Practice specialty automatically receive the GP role. It's possible for a physician to hold multiple specialties and roles simultaneously.
 * Patient Management: Patients can be onboarded by a GP or an admin, ensuring flexibility in patient registration.
 * Visitation Records: Customized visibility ensures that visitation records are private and only accessible to the respective patient, physician, or admin.
 * Sick Leave Management: Integrated creation of sick leave records during visitation documentation for streamlined patient care.

# Getting Started
 * To set up Health-Track:

 * Database Initialization:
  Ensure a database is created with a name matching the one specified in the application.properties file.
  To pre-populate the database with initial data, uncomment the relevant function calls in the DbInit class's run method.

* Role Setup:
  The system initializes four user roles (Patient, Physician, GP, and Admin) during database setup, as detailed in the DbInit file.
    Usage:

* Physician Registration: Can be done via the admin interface or through the registration page accessible before login.
* Patient Creation: Both GPs and admins can create patient profiles.
* Visitation and Sick Leave Records: Managed with role-based access control to ensure privacy and data integrity.

# Project Functionality
This project aims to provide an integrated platform for managing healthcare records, improving the efficiency of medical practices and patient care. By centralizing data and implementing role-based access, Health-Track ensures that sensitive health information is handled securely while remaining accessible to authorized users.
