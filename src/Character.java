public class Character {
    private final String characterId;
    private final int maxHP;
    private final int attack;
    private final int defense;

    public Character(String characterId, int maxHP, int attack, int defense) {
        this.characterId = characterId;
        this.maxHP = maxHP;
        this.attack = attack;
        this.defense = defense;
    }

    public String getCharacterId() {
        return characterId;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }
}