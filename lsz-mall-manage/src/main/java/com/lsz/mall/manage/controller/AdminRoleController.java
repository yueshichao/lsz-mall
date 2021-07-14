package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.AdminMenu;
import com.lsz.mall.base.entity.AdminResource;
import com.lsz.mall.base.entity.AdminRole;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(description = "后台用户角色管理")
@RequestMapping("/role")
public class AdminRoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("添加角色")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage create(@RequestBody AdminRole role) {
        int count = roleService.create(role);
        if (count > 0) {
            return ResponseMessage.ok(count);
        }
        return ResponseMessage.error();
    }

    @ApiOperation("修改角色")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage update(@PathVariable Long id, @RequestBody AdminRole role) {
        int count = roleService.update(id, role);
        if (count > 0) {
            return ResponseMessage.ok(count);
        }
        return ResponseMessage.error();
    }

    @ApiOperation("批量删除角色")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage delete(@RequestParam("ids") List<Long> ids) {
        int count = roleService.delete(ids);
        if (count > 0) {
            return ResponseMessage.ok(count);
        }
        return ResponseMessage.error();
    }

    @ApiOperation("获取所有角色")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<List<AdminRole>> listAll() {
        List<AdminRole> roleList = roleService.list();
        return ResponseMessage.ok(roleList);
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<CommonPage<AdminRole>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<AdminRole> roleList = roleService.getPage(keyword, pageSize, pageNum);
        return ResponseMessage.ok(roleList);
    }

    @ApiOperation("修改角色状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        AdminRole AdminRole = new AdminRole();
        AdminRole.setStatus(status);
        int count = roleService.update(id, AdminRole);
        if (count > 0) {
            return ResponseMessage.ok(count);
        }
        return ResponseMessage.error();
    }

    @ApiOperation("获取角色相关菜单")
    @RequestMapping(value = "/listMenu/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<List<AdminMenu>> listMenu(@PathVariable Long roleId) {
        List<AdminMenu> roleList = roleService.listMenu(roleId);
        return ResponseMessage.ok(roleList);
    }

    @ApiOperation("获取角色相关资源")
    @RequestMapping(value = "/listResource/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<List<AdminResource>> listResource(@PathVariable Long roleId) {
        List<AdminResource> roleList = roleService.listResource(roleId);
        return ResponseMessage.ok(roleList);
    }

    @ApiOperation("给角色分配菜单")
    @RequestMapping(value = "/allocMenu", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = roleService.allocMenu(roleId, menuIds);
        return ResponseMessage.ok(count);
    }

    @ApiOperation("给角色分配资源")
    @RequestMapping(value = "/allocResource", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        return ResponseMessage.ok(count);
    }

}
