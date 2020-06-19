package blockbreakevent.blockbreak;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


public final class BlockBreak extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        //ลงทะเบียนอีเว้นในคลาสนี้
        getServer().getPluginManager().registerEvents(this,this);
        //ส่งข้อความไป cmd เมื่อปลั๊กอินทำงาน
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "Plugin" + ChatColor.GRAY + " : " + ChatColor.GREEN + " Enable");
    }

    @Override
    public void onDisable() {
        //ส่งข้อความไป cmd เมื่อปลั๊กอินไม่ทำงาน
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "Plugin" + ChatColor.GRAY + " : " + ChatColor.RED + "Disable");
    }

    //จะถูกเรียกใช้(ทำงาน)เมื่อผู้เล่นทำลาย Block เราสามารถกดเข้าไปดูคำอธิบายได้โดย Intellij ให้กด Ctrl + คลิกซ้ายที่ BlockBreakEvent
    @EventHandler
    public void onBlockbreak(BlockBreakEvent event){
        //รับค่าผู้เล่นเก็บเอาไว้
        Player player = event.getPlayer();
        //เก็บค่า BlockType ไว้ในตัวแปรชื่อ material ซึ่งเราสามารถนำตัวแปรนี้ไปใช้ได้เหมือนกับ event.getBlock().getType(); จะเห็นได้จากรูปแบบการเขียนด้านล่างนี้
        Material material = event.getBlock().getType();
        //รับ location ของ block มาเก็บไว้
        Location location = event.getBlock().getLocation();
        //ยกเลิกอีเว้นนี้ ตีไปแล้วไม่แตกอย่างเช่นใน world guard
        event.setCancelled(true);
        //สร้างไอเทมดรอปออกมาจาก block โดย dropItemnaturally จะดรอปแบบบล็อคถูกทำลาย สุ่มทิศทางมั่วๆรอบๆ
        event.getBlock().getWorld().dropItemNaturally(location,new ItemStack(material));
        //เซ็ตblock ตรงโลเคชั่นนั้นเป็น bedrock
        location.getBlock().setType(Material.BEDROCK);
        //ใน if นี้จะทำการตรวจสอบ Block ว่าคือ Block ชนิดไหน สามารถใช้คำสั่งแบบนี้ได้
        if(event.getBlock().getType() == Material.DIRT){
            //ส่ง message ไปยังผู้เล่นโดยประกอบด้วย สี Block(if) < ค่าสตริง + กับชนิดของ Block **ใน Java การใช้เครื่องหมาย concatenating (+) กับสตริง และค่าอื่นจะเป็นการต่อสตริง
            //จาก Material material = event.getBlock().getType(); ด้านบนจะเห็นว่ามันรีเทินค่ากลับมาเป็น Material แต่เราก็นำมันมาต่อสตริงได้
            player.sendMessage(ChatColor.GREEN + "Block(if) : " + event.getBlock().getType());

            //โดยเราสามารถใส่ return ใน if ทำให้เมื่อทำงานในเงื่อนไขนี้เสร็จจะจบการทำงานในรอบนี้ทันที
            //return;

        }
        else if(event.getBlock().getType() == Material.SAND){
            player.sendMessage(ChatColor.GREEN + "Block(if) : " + event.getBlock().getType());
        }
        //รูปแบบนี้คือการตรวจสอบเงื่อนไขโดยใช้ or แทนด้วยสัญลักษณ์ || นั่นเอง
        else if(event.getBlock().getType() == Material.DIAMOND_ORE || event.getBlock().getType() == Material.GOLD_ORE ||
                event.getBlock().getType() == Material.IRON_ORE ){
            player.sendMessage(ChatColor.GREEN + "Block(if) : " + event.getBlock().getType());

        }

        //อีกรูปแบบนึงในการตรวจสอบว่า Block นั้นเป็นชนิดไหน (เเบบนี้เหมาะสำหรับการตรวจสอบ Block หลายชนิด ลองคิดดูถ้าต้องเขียน if เป็น 100 บรรทัด
        switch (event.getBlock().getType()){
            //ตรวจสอบว่าคือ DIRT หรือไม่ถ้าใช้ก็จะเข้าไปทำงาน
            case DIRT:
                //เราสามารถใส่ ifelse ใน case ได้
                if(event.getBlock().getType() == Material.DIRT) {
                    player.sendMessage(ChatColor.RED + "Block(switch) if : " + material);

                }else {
                    player.sendMessage(ChatColor.RED + "Block(switch) else : " + material);
                }
                //break;ใช้เพื่อหยุด หมายความว่า เมื่อเจอ case ที่ตรงกันแล้วจะออกจาก switch ทันที
                break;
            case SAND:
                player.sendMessage(ChatColor.RED + "Block(switch) : " + material);
                break;
            //รูปแบบด้านล่างเปรียบเสมือน or ก็ไม่เชิง เพราะมันสามารถตรวจสอบได้ถึง 3 ชนิด(สามารถเพิ่มได้) สมมุติว่าเราทำลาย Diamon มันก็จะแสดงผลเป็น Block : Diamon
            //แต่ถ้าเราทำลาย Gold_Ore มันก็จะแสดงผลเป็น Block : Gold
            case DIAMOND_ORE:
            case GOLD_ORE:
            case IRON_ORE:
                player.sendMessage(ChatColor.RED + "Block(switch) : " + material);
                //เราสามารถใส่ return ใน switch case ได้เช่นกัน
                //return;
                break;

        }


        //BlockBreak นั้นแทบจะเป็น Event หลักๆที่หลายปลั๊กอินใช้กัน ผมจะยกตัวอย่างการทำ Regenblock Block แบบง่ายๆ
        //รูปแบบด้านล่างเป็นการเขียนแบบลวกๆ เราควรจะทำฟังชันก์แยกและไม่ควรเรียกใช้ runnable เยอะเกินไป
        new BukkitRunnable() {
            @Override
            public void run() {
                //รีเซ็ต Block ที่แตก ด้วยการ setType Material = Block ที่เราทำลาย event.getblock นั้นจะชี้ไปที่ block ที่โดนทำลาย
                event.getBlock().setType(material);
                player.sendMessage(ChatColor.AQUA + "Block Location : " + event.getBlock().getLocation());
            }
        }.runTaskLater(this, 100);//จะทำงานใน void run เมื่อผ่านไป 5วินาที 1วินาที = 20
        //เช็คบล็อคสุดท้าย อยากถามตัวเองว่าใส่ทำไม
        player.sendMessage(ChatColor.GREEN + "Block(Last) : " + event.getBlock().getType());

    }
}
