package com.echo.login;

import com.echo.aroute.ARouter;
import com.echo.aroute.IRouter;

public class ActivityUtil implements IRouter {
    @Override
    public void putActivity() {
        ARouter.getInstance().putActivity("login/login",LoginActivity.class);
    }
}
