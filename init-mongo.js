db.createUser({
    user: "foodpanda_user", pwd: "1234", roles:
        [{role: "readWrite", db: "foodpanda"}]
})