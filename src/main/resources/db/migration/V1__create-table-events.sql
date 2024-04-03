CREATE TABLE events (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    detailts VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    maximum_attendees INTEGER NOT NULL
);
