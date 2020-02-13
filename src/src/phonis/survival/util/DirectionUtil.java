package src.phonis.survival.util;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.util.Vector;

import java.util.List;

public class DirectionUtil {
    public static void faceDirection(Player player, Location target) {
        if (player.isInsideVehicle()) {
            Vehicle v = (Vehicle) player.getVehicle();

            if (v == null) return;

            List<Entity> passengers = v.getPassengers();
            
            DirectionUtil.face(player, target);
            
            for(Entity entity : passengers) {
            	v.addPassenger(entity);
            }
        }else {
        	DirectionUtil.face(player, target);
        }
    }

	private static void face(Player player, Location target) {
        Vector direction = target.clone().subtract(player.getEyeLocation()).toVector();
        Location location = player.getLocation().setDirection(direction);
        player.teleport(location);
	}

	public static String getCardinalAbsoluteDirection(Player player) {
        double rotation = (player.getLocation().getYaw() + 180) % 360;
        
        if (rotation < 0) {
            rotation += 360.0;
        }
        
        if (0 <= rotation && rotation < 45) {
            return "N";
        }else if (45 <= rotation && rotation < 135) {
            return "E";
        }else if (135 <= rotation && rotation < 225) {
            return "S";
        }else if (225 <= rotation && rotation < 315) {
            return "W";
        }else if (315 <= rotation && rotation < 360) {
            return "N";
        }else {
            return null;
        }
	}

	public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() + 180) % 360;
        
        if (rotation < 0) {
            rotation += 360.0;
        }
        
        if (0 <= rotation && rotation < 22.5) {
            return "N";
        }else if (22.5 <= rotation && rotation < 67.5) {
            return "NE";
        }else if (67.5 <= rotation && rotation < 112.5) {
            return "E";
        }else if (112.5 <= rotation && rotation < 157.5) {
            return "SE";
        }else if (157.5 <= rotation && rotation < 202.5) {
            return "S";
        }else if (202.5 <= rotation && rotation < 247.5) {
            return "SW";
        }else if (247.5 <= rotation && rotation < 292.5) {
            return "W";
        }else if (292.5 <= rotation && rotation < 337.5) {
            return "NW";
        }else if (337.5 <= rotation && rotation < 360.0) {
            return "N";
        }else {
            return null;
        }
    }

	public static void printSlimeMap(Player player) {
        Chunk chunk = player.getLocation().getChunk();
        World world = chunk.getWorld();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        int widthRadius = 20;
        int heightRadius = 6;
        String[][] map = new String[(heightRadius * 2) + 3][(widthRadius * 2) + 3];
        
        for (int z = chunkZ - heightRadius; z <= chunkZ + heightRadius; z++) {
        	for (int x = chunkX - widthRadius; x <= chunkX + widthRadius; x++) {
        	    int row = z + -1 * (chunkZ - heightRadius - 1);
        	    int col = x + -1 * (chunkX - widthRadius - 1);

        		if (world.isChunkLoaded(x, z)){
    				if (world.getChunkAt(x, z).isSlimeChunk()) {
    					map[row][col] = ChatColor.GREEN + "Y";
    				}else {
    					map[row][col] = ChatColor.RED + "N";
    				}
        		}else {
        			map[row][col] = ChatColor.GRAY + "-";
        		}
        	}
        }
        
        map[0][1] = ChatColor.WHITE + "N";
        map[1][0] = ChatColor.WHITE + "W";
        map[1][2] = ChatColor.WHITE + "E";
        map[2][1] = ChatColor.WHITE + "S";
        map[0][0] = ChatColor.WHITE + "\\";
        map[0][2] = ChatColor.WHITE + "/";
        map[2][0] = ChatColor.WHITE + "/";
        map[2][2] = ChatColor.WHITE + "\\";
        map[1][1] = ChatColor.GRAY + "-";
        map[heightRadius + 1][widthRadius + 1] = ChatColor.YELLOW + ChatColor.stripColor(map[heightRadius + 1][widthRadius + 1]);
        
        String direction = DirectionUtil.getCardinalDirection(player);

        if (direction == null) return;

        switch(direction){
        	case "N":
        		map[0][1] = ChatColor.BLUE + "N";
        		
        		break;
        	case "W":
        		map[1][0] = ChatColor.BLUE + "W";
        		
        		break;
        	case "E":
        		map[1][2] = ChatColor.BLUE + "E";
        		
        		break;
        	case "S":
        		map[2][1] = ChatColor.BLUE + "S";
        		
        		break;
        	case "NE":
        		map[0][2] = ChatColor.BLUE + "/";
        		
        		break;
        	case "NW":
        		map[0][0] = ChatColor.BLUE + "\\";
        		
        		break;
        	case "SE":
        		map[2][2] = ChatColor.BLUE + "\\";
        		
        		break;
        	case "SW":
        		map[2][0] = ChatColor.BLUE + "/";
        		
        		break;
        	default:
        		break;
        }
        
        StringBuilder message = new StringBuilder();
        
        for (int row = 0; row < (heightRadius * 2) + 3; row++) {
        	for (int col = 0; col < (widthRadius * 2) + 3; col++) {
        		if (map[row][col] == null) {
                    message.append(ChatColor.DARK_GRAY).append("&");
        		}else {
        			message.append(map[row][col]);
        		}
        	}
        	
        	message.append("\n");
        }
        
        player.sendMessage("" + message);
        
        player.sendMessage(
        	ChatColor.GOLD + 
        	"Current chunk: (" + chunkX + ", " + chunkZ + ") Slime chunk: " + 
        	chunk.isSlimeChunk()
        );
	}
}
