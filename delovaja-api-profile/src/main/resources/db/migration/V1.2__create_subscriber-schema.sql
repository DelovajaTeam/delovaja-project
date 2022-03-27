CREATE TABLE IF NOT EXISTS profile.subscribers (
  id serial PRIMARY KEY,
  first_name VARCHAR(20) NOT NULL,
  last_name VARCHAR(30) NOT NULL,
  email VARCHAR(256) UNIQUE NOT NULL,
  organization_id BIGINT NOT NULL,
  position VARCHAR(100) NOT NULL,
  FOREIGN KEY(organization_id)
    REFERENCES profile.organizations(id)
)