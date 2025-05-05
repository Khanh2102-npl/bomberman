package entities.tile.item;

import entities.Entity;
import entities.character.Bomber;
import entities.tile.Tile;
import graphics.Sprite;
import sound.Sound;

public abstract class Item extends Tile{
    protected int duration = -1; //thoi gian cua item ,-1 la vo han
	protected boolean active = false;
	protected int level;
	public Item(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
}
