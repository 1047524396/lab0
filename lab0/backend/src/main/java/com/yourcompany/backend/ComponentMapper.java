@Mapper
public interface ComponentMapper {
    @Select("SELECT * FROM components")
    List<Component> getAllComponents();

    @Select("SELECT * FROM components WHERE id = #{id}")
    Component getComponentById(Long id);

    @Insert("INSERT INTO components (name, version, supplier, package_manager, created_at) " +
            "VALUES (#{name}, #{version}, #{supplier}, #{packageManager}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void createComponent(Component component);

    @Delete("DELETE FROM components WHERE id = #{id}")
    void deleteComponent(Long id);
}
