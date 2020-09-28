package dev.assist.multiselect.edit;

/**
 * detail: 多选编辑接口
 * @author Ttt
 * <pre>
 *     主要用于 Adapter 实现该接口, 对外支持快捷操作方法
 *     内部通过 MultiSelectListAssist、MultiSelectMapAssist 实现多选操作功能
 * </pre>
 */
public interface IMultiSelectEdit {

    // ===========
    // = 编辑状态 =
    // ===========

    /**
     * 是否编辑状态
     * @return {@code true} yes, {@code false} no
     */
    boolean isEditState();

    /**
     * 设置编辑状态
     * @param isEdit {@code true} yes, {@code false} no
     * @return {@link IMultiSelectEdit}
     */
    IMultiSelectEdit setEditState(boolean isEdit);

    /**
     * 切换编辑状态
     * @return {@link IMultiSelectEdit}
     */
    IMultiSelectEdit toggleEditState();

    // ===========
    // = 选择操作 =
    // ===========

    /**
     * 全选
     * @return {@link IMultiSelectEdit}
     */
    IMultiSelectEdit selectAll();

    /**
     * 清空全选 ( 非全选 )
     * @return {@link IMultiSelectEdit}
     */
    IMultiSelectEdit clearSelectAll();

    /**
     * 反选
     * @return {@link IMultiSelectEdit}
     */
    IMultiSelectEdit inverseSelect();

    // ===========
    // = 判断处理 =
    // ===========

    /**
     * 判断是否全选
     * @return {@code true} yes, {@code false} no
     */
    boolean isSelectAll();

    /**
     * 判断是否存在选中的数据
     * @return {@code true} yes, {@code false} no
     */
    boolean isSelect();

    /**
     * 判断是否不存在选中的数据
     * @return {@code true} yes, {@code false} no
     */
    boolean isNotSelect();

    // =========
    // = 数量值 =
    // =========

    /**
     * 获取选中的数据条数
     * @return 选中的数据条数
     */
    int getSelectSize();

    /**
     * 获取数据总数
     * @return 数据总数
     */
    int getDataCount();
}