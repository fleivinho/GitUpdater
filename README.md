# GitUpdater
🚀 **The Most Effective Plugin Updater with GitHub**

GitUpdater is a powerful and flexible updater designed to seamlessly integrate with GitHub. You can also customize it to work with other update systems, such as your own websites or APIs.

---

## ✨ Features
- **Spigot Compatibility**
- **BungeeCord compatibility** _(soon)_
- **GitHub Integration**: Effortlessly fetch updates directly from your GitHub repository.
- **Customizable**: Easily adapt to use alternative update systems, including private servers.
- **Easy to Use**: Intuitive implementation for quick integration.
- **Lightweight & Efficient**: Minimal impact on performance while maintaining robust functionality.
---

## 🔧 How to Apply
```java
public class ExamplePlugin extends JavaPlugin {

    private BukkitUpdater updater;
    private int updateTask;

    @Override
    public void onEnable() {
        // updater = new BukkitUpdater(plugin, organization, repository);
        updater = new BukkitUpdater(this, "fleivinho", "GitUpdater");

        // Auto update task
        updateTask = Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            updater.checkUpdate();
        }, 20L*300, 20*300);
    }

    @Override
    public void onDisable() {
        if (updateTask > 0) {
            Bukkit.getScheduler().cancelTask(updateTask);
        }
        updater = null;
    }

}

```
