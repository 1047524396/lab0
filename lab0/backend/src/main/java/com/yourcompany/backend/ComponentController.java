@RestController
@RequestMapping("/api/components")
public class ComponentController {
    private final ComponentService componentService;

    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @GetMapping
    public List<Component> getAllComponents() {
        return componentService.getAllComponents();
    }

    @GetMapping("/{id}")
    public Component getComponentById(@PathVariable("id") Long id) {
        return componentService.getComponentById(id);
    }

    @PostMapping
    public Component createComponent(@RequestBody Component component) {
        return componentService.createComponent(component);
    }

    @DeleteMapping("/{id}")
    public void deleteComponent(@PathVariable("id") Long id) {
        componentService.deleteComponent(id);
    }
}
