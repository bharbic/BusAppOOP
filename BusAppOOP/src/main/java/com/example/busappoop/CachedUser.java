package com.example.busappoop;

public class CachedUser extends User {

    private String cachedUsername;
    private String cachedPassword;

    private CachedUser() {}
    private static final CachedUser instance = new CachedUser();

    public static CachedUser getInstance() {
        return instance;
    }

    public static void cacheUserData(User user) {
        instance.cachedUsername = user.getUsername();
        instance.cachedPassword = user.getPassword();
    }

    public static void clearUserData() {
        instance.cachedUsername = null;
        instance.cachedPassword = null;
    }

    public String getUsername() {
        return instance.cachedUsername;
    }

    public String getPassword() {
        return instance.cachedPassword;
    }


}

