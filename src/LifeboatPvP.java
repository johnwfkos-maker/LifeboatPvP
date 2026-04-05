import cn.nukkit.plugin.PluginBase;
import cn.nukkit.event.Listener;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.entity.Entity;
import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;

import java.util.HashMap;

public class LifeboatPvP extends PluginBase implements Listener {

    private HashMap<Player, Long> lastHit = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        if (!(e.getEntity() instanceof Entity)) return;

        Player attacker = (Player) e.getDamager();
        Entity victim = e.getEntity();

        long now = System.currentTimeMillis();
        long last = lastHit.getOrDefault(attacker, 0L);
        lastHit.put(attacker, now);

        Item item = attacker.getInventory().getItemInHand();

        double kb = 0.4;
        double vertical = 0.35;

        if (item.isSword() && (now - last < 400)) {
            kb *= 1.5;
        }

        if (attacker.isSprinting()) {
            kb *= 1.2;
        }

        Vector3 dir = victim.subtract(attacker).normalize();

        victim.setMotion(new Vector3(
                dir.x * kb,
                vertical,
                dir.z * kb
        ));
    }
          }
