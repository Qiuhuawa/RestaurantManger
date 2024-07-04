<style>
.navigation { margin-left: 0px; height: 80%; }
.navigation div { padding-left: 1px; }
.navigation div ul { overflow: hidden; display: none; height: auto; margin: 0; margin-left: -.8rem; }
.navigation span { display: block; line-height: 25px; padding: .5rem .5rem; cursor: pointer; }
.navigation span:hover { background-color: rgba(0, 0, 0, 0.03); }
.navigation a { color: #333; text-decoration: none; }
.navigation a:hover { color: #06F; }
</style>
<aside class="sidebar">
    <div class="scrollbar-inner">
        <div class="user">
            <div class="user__info" data-toggle="dropdown">
                <img class="user__img">
                <div>
                    <div class="user__name">欢迎您：</div>
                    <div class="user__email">${realName}</div>
                </div>
            </div>
            <div class="dropdown-menu">
                <!-- <a class="dropdown-item" href="#" id="user__profile">个人信息</a> -->
                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#update-password">修改密码</a>
            </div>
        </div>
        <div id="navigation" class="navigation">
        
<#--        <div><span name="/dict/listdict" onclick='loadMain(this);'>-->
<#--           <i class=''></i>&nbsp;&nbsp;字典管理</span><ul>-->
<#--        </ul></div>-->
<#--        -->
<#--        <div><span name="/org/listorganization" onclick='loadMain(this);'>-->
<#--           <i class=''></i>&nbsp;&nbsp;组织管理</span><ul>-->
<#--        </ul></div>-->
<#--        -->
<#--        <div><span name="/res/listresource" onclick='loadMain(this);'>-->
<#--           <i class=''></i>&nbsp;&nbsp;资源管理</span><ul>-->
<#--        </ul></div>-->
        
		</div>
    </div>
</aside>
