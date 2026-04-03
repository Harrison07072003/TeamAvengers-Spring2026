public class Monster extends Character{
    //fields
    String monsterDescription;
    String currentRoom;
    //constructor
    public Monster(String id,String description, int maxHP, int attack, int defense, String roomID) {
        super(id, maxHP, attack, defense);
        this.monsterDescription = description;
        this.currentRoom = roomID;
    }
    //methods
    //getters and setters
    public String getMonsterDescription() {
        return this.monsterDescription;
    }
    public void attackPlayer(PlayerT player){
        int damage = this.getAttack() - player.getDefense();
        player.setCurrentHP(player.getCurrentHP() - damage);
        if(player.getCurrentHP() <= 0)
            player.setAlive(false);
    }
}
