db.createUser(
    {
        user: "usr-series-mongo",
        pwd: "pwd-series-mongo",
        roles: [
            {
                role: "readWrite",
                db: "series-dev"
            }
        ]
    }
);