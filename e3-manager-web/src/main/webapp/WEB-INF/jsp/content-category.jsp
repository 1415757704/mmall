<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	 <ul id="contentCategory" class="easyui-tree">
    </ul>
</div>
<!-- 为菜单添加事件 -->
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
$(function(){
	$("#contentCategory").tree({
		url : '/content/category/list',
		animate: true,
		method : "GET",
		onContextMenu: function(e,node){//右键时调用的方法
            e.preventDefault();
            $(this).tree('select',node.target);//选中那个被右击的节点
            $('#contentCategoryMenu').menu('show',{//显示右击菜单
                left: e.pageX,
                top: e.pageY
            });
        },
        onAfterEdit : function(node){
        	var _tree = $(this);
        	if(node.id == 0){//id为0表示新增的节点
        		// 新增节点
        		$.post("/content/category/create",{parentId:node.parentId,name:node.text},function(data){
        			if(data.status == 200){
        				_tree.tree("update",{
            				target : node.target,
            				id : data.data.id
            			});
        				$.messager.alert('提示','创建成功!');
        			}else{
        				$.messager.alert('提示','创建'+node.text+' 分类失败!');
        			}
        		});
        	}else{//重命名在光标离开的时候也会触发该事件
        		$.post("/content/category/update",{id:node.id,name:node.text},function(data){
        			if (data.status==200){
        				$.messager.alert('提示','更新成功!');
        			}else {
        				$.messager.alert('提示','更新失败!');
					}
        		});
        	}
        }
	});
});
function menuHandler(item){
	var tree = $("#contentCategory");
	var node = tree.tree("getSelected");//找到被选中的node节点
	if(item.name === "add"){
		tree.tree('append', {
            parent: (node?node.target:null),
            data: [{
                text: '新建分类',
                id : 0,//新添加的节点id为0，所以就是根据这个去找到并选中那些还未保存到数据库中的节点
                parentId : node.id
            }]
        }); 
		var _node = tree.tree('find',0);//找到id为0的节点、也就是新添加的节点
		tree.tree("select",_node.target).tree('beginEdit',_node.target);//变为可编辑状态，在光标离开的时候触发另一个事件onAfterEdit
	}else if(item.name === "rename"){
		tree.tree('beginEdit',node.target);
	}else if(item.name === "delete"){
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			if(r){
				$.post("/content/category/delete",{id:node.id},function(data){
					tree.tree("remove",node.target);
					$.messager.alert('提示','删除成功!');
				});	
			}
		});
	}
}
</script>