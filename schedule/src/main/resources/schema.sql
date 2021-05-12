CREATE TABLE IF NOT EXISTS Student (
    id bigserial,
    name varchar(100) NOT NULL,
    CONSTRAINT "Student_pkey" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Lesson (
    id bigserial,
    sku varchar(100) NOT NULL,
    CONSTRAINT "Lesson_pkey" PRIMARY KEY (id)
);