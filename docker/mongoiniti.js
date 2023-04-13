db.createUser(
    {
        user: "usr-catalog-mongo",
        pwd: "pwd-catalog-mongo",
        roles: [
            {
                role: "readWrite",
                db: "catalog-dev"
            }
        ]
    }
);