package cx.mia.samsara.api;

import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import cx.mia.samsara.Samsara;
import xyz.derkades.derkutils.bukkit.LocationUtils;

public class Room implements Listener {

    private final String name;
    private final Location cornerA;
    private final Location cornerB;
    private final List<Sound> sounds;
//    private final Set<UUID> playersInRoom = new HashSet<>();

    /**
     * A Room is a defined space within a minecraft world that will play sounds while the player resides within them.
     *
     * @param name   name of the room
     * @param c1     first corner of the room (cuboid)
     * @param c2     second corner of the room (cuboid)
     * @param sounds a list of sounds sound associated with this room (these will play when entering the room, see {@link RoomEnterListener})
     */
    public Room(final String name, final Location cornerA, final Location cornerB, final List<Sound> sounds) {
        this.name = name;
        this.cornerA = cornerA;
        this.cornerB = cornerB;
        this.sounds = sounds;

        getModule().getLogger().debug("Declared room with name: %s ", name);
        getModule().getLogger().debug("Room %s has corners (%s, %s) and (%s, %s)", name, cornerA.getX(), cornerA.getY(), cornerA.getZ(), cornerB.getX(), cornerB.getY(), cornerB.getZ());
        getModule().getLogger().debug("Room %s has sounds %s", name, sounds);
    }

	public void onPlayerEnter(final Player player, final boolean wasAlreadyInBounds) {
		playSounds(player);
		getModule().getLogger().debug(player.getName() + " entered " + this.getName() + ", so all its sounds were triggered");
	}

    public void onPlayerLeave(final Player player, final Optional<Room> newSubRoom) {
    	stopSounds(player);
    	getModule().getLogger().debug(player.getName() + " entered a room other than " + this.getName() + ", so all its sounds were stopped");

    	if (newSubRoom.isPresent()) {
    		// doe iets als de speler nog steeds in deze kamer is en naar een subroom is gegaan
    	} else {
    		// doe iets als de speler uit deze kamer is gegaan zonder naar een subroom te gaan
    	}
    }

    public void playSounds(final Player player) {
        this.sounds.forEach(sound -> {
            sound.playSound(player);
            getModule().getLogger().debug("Sound " + sound.getSound() + " was triggered by " + player.getName());
        });
    }

    public void stopSounds(final Player player) {
        this.sounds.forEach(sound -> {
            sound.stopSound(player);
            getModule().getLogger().debug("Stopping sound " + sound.getSound() + " for player " + player.getName());
        });
    }

    public boolean isInRoomBounds(final Player player) {
       return isInRoomBounds(player.getLocation());
    }

    public boolean isInRoomBounds(final Location location) {
        return LocationUtils.isIn3dBounds(location, this.cornerA, this.cornerB);
    }

    private Samsara getModule() {
        return Samsara.getInstance();
    }

    public String getName() {
        return this.name;
    }

    public List<Sound> getSounds() {
        return this.sounds;
    }

//    public boolean isInRoom(final Player player) {
//    	return this.playersInRoom.contains(player.getUniqueId());
//    }
//
//    public void addPlayer(final Player player) {
//        this.playersInRoom.add(player.getUniqueId());
//    }
//
//    public void removePlayer(final Player player) {
//    	this.playersInRoom.remove(player.getUniqueId());
//    }

}
