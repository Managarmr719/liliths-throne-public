package com.lilithsthrone.game.inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.inventory.enchanting.TFEssence;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.game.inventory.item.ItemEffect;
import com.lilithsthrone.game.inventory.item.ItemEffectType;
import com.lilithsthrone.utils.Colour;
import com.lilithsthrone.utils.XMLSaving;

/**
 * @since 0.1.0
 * @version 0.1.89
 * @author Innoxia
 */
public abstract class AbstractCoreItem implements Serializable, XMLSaving {

	private static final long serialVersionUID = 1L;

	protected String name, namePlural, SVGString;
	protected Colour colourShade;
	protected Rarity rarity;

	protected Map<Attribute, Integer> attributeModifiers;
	protected TFEssence relatedEssence;

	public AbstractCoreItem(String name, String namePlural, String SVGString, Colour colour, Rarity rarity, Map<Attribute, Integer> attributeModifiers) {
		super();
		this.name = name;
		this.namePlural = namePlural;
		this.colourShade = colour;
		this.rarity = rarity;
		this.SVGString = SVGString;

		this.attributeModifiers = new EnumMap<>(Attribute.class);
		
		relatedEssence = null;

		if (attributeModifiers != null)
			for (Entry<Attribute, Integer> e : attributeModifiers.entrySet())
				this.attributeModifiers.put(e.getKey(), e.getValue());

	}
	
	public Element saveAsXML(Element parentElement, Document doc) {
		System.err.print("Eek! Tried to export an abstract item!");
		return null;
	}
	
	public static AbstractCoreItem loadFromXML(Element parentElement, Document doc) {
		System.err.print("Eek! Tried to import an abstract item!");
		return null;
	}
	
	// Enchantments:
	
	public boolean isAbleToBeEnchanted() {
		return getEnchantmentEffect() != null
				&& getEnchantmentItemType(null) != null;
	}
	
	public int getEnchantmentLimit() {
		return 100;
	}
	
	public ItemEffectType getEnchantmentEffect() {
		return null;
	}
	
	public AbstractCoreType getEnchantmentItemType(List<ItemEffect> effects) {
		return null;
	}
	
	public AbstractCoreItem enchant(TFEssence essence, TFModifier primaryModifier, TFModifier secondaryModifier) {
		return this;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TFEssence getRelatedEssence() {
		return relatedEssence;
	}
	public void setRelatedEssence(TFEssence relatedEssence) {
		this.relatedEssence = relatedEssence;
	}

	// Other:
	
	@Override
	public boolean equals (Object o) {
		if(o instanceof AbstractCoreItem){
			if(((AbstractCoreItem)o).getName().equals(this.getName())
				&& ((AbstractCoreItem)o).getColour() == this.getColour()
				&& ((AbstractCoreItem)o).getRarity() == this.getRarity()
				&& ((AbstractCoreItem)o).getAttributeModifiers().equals(this.getAttributeModifiers())
				&& ((AbstractCoreItem)o).getEnchantmentEffect() == getEnchantmentEffect()
				&& ((AbstractCoreItem)o).getEnchantmentItemType(null) == getEnchantmentItemType(null)
				&& ((AbstractCoreItem)o).getRelatedEssence() == getRelatedEssence()){
					return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + this.getName().hashCode();
		result = 31 * result + this.getColour().hashCode();
		result = 31 * result + this.getRarity().hashCode();
		result = 31 * result + this.getAttributeModifiers().hashCode();
		if(getEnchantmentEffect()!=null)
			result = 31 * result + getEnchantmentEffect().hashCode();
		if(getEnchantmentItemType(null)!=null)
		result = 31 * result + getEnchantmentItemType(null).hashCode();
		if(getRelatedEssence()!=null)
			result = 31 * result + getRelatedEssence().hashCode();
		return result;
	}
	
	public String getName() {
		return name;
	}
	
	public String getNamePlural() {
		return namePlural;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSVGString() {
		return SVGString;
	}
	
	public void setSVGString(String SVGString) {
		this.SVGString = SVGString;
	}

	public abstract String getDescription();

	public abstract int getValue();
	
	public int getPrice(float modifier) {
		return (int) (getValue() * modifier);
	}

	public Rarity getRarity() {
		return rarity;
	}
	
	/**
	 * @return the name of a css class to use as a displayed rarity in inventory screens
	 */
	public String getDisplayRarity() {
		return getRarity().getName();
	}

	public Colour getColour() {
		return colourShade;
	}

	public void setColour(Colour Colour) {
		this.colourShade = Colour;
	}

	public Map<Attribute, Integer> getAttributeModifiers() {
		return attributeModifiers;
	}

	public void setAttributeModifiers(Map<Attribute, Integer> attributeModifiers) {
		this.attributeModifiers = attributeModifiers;
	}
	
	public List<ItemEffect> getEffects() {
		return new ArrayList<ItemEffect>();
	}
}
