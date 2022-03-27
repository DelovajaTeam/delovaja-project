CREATE TABLE IF NOT EXISTS profile.organizations (
  id BIGSERIAL UNIQUE PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  tax_number VARCHAR(10) UNIQUE NOT NULL,
  state_registration_number VARCHAR(13) UNIQUE NOT NULL,
  address VARCHAR(255) NOT NULL,
  work_area VARCHAR(100)
)