###添加依赖
'compile'com.lyf:yflibrary:1.0.2''
###请求权限:
'private String[] REQUEST_PERMISSIONS = new String[]{Manifest.permission.CAMERA};
  Permission.checkPermisson(this, REQUEST_PERMISSIONS, new PermissionResult() {

            @Override
            public void success() {
                //成功
            }

            @Override
            public void fail() {
                //失败
            }
        });'
