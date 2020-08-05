/*     */ package com.mralby.atlasenchants;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.ConsoleCommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.FileConfigurationOptions;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ 
/*     */ public class Main extends JavaPlugin
/*     */ {
/*  26 */   public HashMap<Player, Boolean> hasHelmet = new HashMap();
/*  27 */   public HashMap<Player, List<Entity>> playerEntities = new HashMap();
/*  28 */   public HashMap<Player, BukkitTask> ColorTask = new HashMap();
/*     */ 
/*     */   public Inventory fabbroGUI(Player player) {
/*  31 */     Inventory inv_fabbro = Bukkit.createInventory(player, InventoryType.CHEST, 
/*  32 */       ChatColor.DARK_GRAY + ChatColor.BOLD + "Blacksmith");
/*     */ 
/*  34 */     ItemStack vetro = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
/*  35 */     ItemMeta vetroMeta = vetro.getItemMeta();
/*  36 */     vetroMeta.setDisplayName(" ");
/*  37 */     vetro.setItemMeta(vetroMeta);
/*     */ 
/*  39 */     ItemStack vetroslot1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
/*  40 */     ItemMeta vetroslot1Meta = vetroslot1.getItemMeta();
/*  41 */     vetroslot1Meta.setDisplayName(" ");
/*  42 */     vetroslot1.setItemMeta(vetroslot1Meta);
/*     */ 
/*  44 */     ItemStack pulsante = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
/*  45 */     ItemMeta pulsanteMeta = pulsante.getItemMeta();
/*  46 */     pulsanteMeta.setDisplayName(ChatColor.GREEN + ChatColor.BOLD + "Upgrade");
/*  47 */     pulsante.setItemMeta(pulsanteMeta);
/*     */ 
/*  49 */     inv_fabbro.setItem(0, vetro);
/*  50 */     inv_fabbro.setItem(1, vetro);
/*  51 */     inv_fabbro.setItem(2, vetroslot1);
/*  52 */     inv_fabbro.setItem(3, vetro);
/*  53 */     inv_fabbro.setItem(4, vetro);
/*  54 */     inv_fabbro.setItem(5, vetro);
/*  55 */     inv_fabbro.setItem(6, vetroslot1);
/*  56 */     inv_fabbro.setItem(7, vetro);
/*  57 */     inv_fabbro.setItem(8, vetro);
/*  58 */     inv_fabbro.setItem(9, vetro);
/*  59 */     inv_fabbro.setItem(10, vetroslot1);
/*  60 */     inv_fabbro.setItem(12, vetroslot1);
/*  61 */     inv_fabbro.setItem(13, vetro);
/*  62 */     inv_fabbro.setItem(14, vetroslot1);
/*  63 */     inv_fabbro.setItem(16, vetroslot1);
/*  64 */     inv_fabbro.setItem(17, vetro);
/*  65 */     inv_fabbro.setItem(18, vetro);
/*  66 */     inv_fabbro.setItem(19, vetro);
/*  67 */     inv_fabbro.setItem(20, vetroslot1);
/*  68 */     inv_fabbro.setItem(21, vetro);
/*  69 */     inv_fabbro.setItem(22, pulsante);
/*  70 */     inv_fabbro.setItem(23, vetro);
/*  71 */     inv_fabbro.setItem(24, vetroslot1);
/*  72 */     inv_fabbro.setItem(25, vetro);
/*  73 */     inv_fabbro.setItem(26, vetro);
/*  74 */     return inv_fabbro;
/*     */   }
/*     */ 
/*     */   public int getEmptySlots(Player p) {
/*  78 */     Inventory inventory = p.getInventory();
/*  79 */     ItemStack[] cont = inventory.getContents();
/*  80 */     int i = 0;
/*  81 */     for (ItemStack item : cont) {
/*  82 */       if ((item != null) && (item.getType() != Material.AIR))
/*  83 */         i++;
/*     */     }
/*  85 */     return 36 - i;
/*     */   }
/*     */ 
/*     */   public void onEnable()
/*     */   {
/*  91 */     Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "AtlasEnchants ON");
/*  92 */     getConfig().options().copyDefaults(true);
/*  93 */     saveDefaultConfig();
/*     */ 
/*  95 */     getServer().getPluginManager().registerEvents(new Events(this), this);
/*     */   }
/*     */ 
/*     */   public void onDisable()
/*     */   {
/* 100 */     Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "AtlasEnchants OFF");
/*     */   }
/*     */ 
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/* 104 */     if (cmd.getName().equalsIgnoreCase("ae")) {
/* 105 */       if (args.length == 0) {
/* 106 */         if ((sender instanceof Player)) {
/* 107 */           Player p = (Player)sender;
/* 108 */           if (p.getInventory().firstEmpty() != -1) {
/* 109 */             if (getEmptySlots(p) >= 2)
/* 110 */               p.openInventory(fabbroGUI(p));
/*     */           }
/*     */           else
/*     */           {
/* 114 */             String fullmessage = getConfig().getString("inventory-full-message-onopen");
/* 115 */             fullmessage = fullmessage.replaceAll("&", "§");
/* 116 */             p.sendMessage(fullmessage);
/* 117 */             p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/* 122 */       else if (sender.hasPermission("ae.admin")) {
/* 123 */         if (args[0].equalsIgnoreCase("help")) {
/* 124 */           String prefix = getConfig().getString("tag-prefix");
/* 125 */           prefix = prefix.replaceAll("&", "§");
/* 126 */           sender.sendMessage("\n" + prefix);
/* 127 */           sender.sendMessage("/ae give [player] [quantity] | §7Give you the custom item");
/* 128 */           sender.sendMessage("/ae reload | §7Reload the config");
/* 129 */           return true;
/*     */         }
/* 131 */         if (args[0].equalsIgnoreCase("reload")) {
/* 132 */           reloadConfig();
/* 133 */           sender.sendMessage(ChatColor.GREEN + "Config Reloaded!");
/* 134 */           return true;
/*     */         }
/* 136 */         if (args.length == 3) {
/* 137 */           if (Bukkit.getPlayer(args[1]) != null) {
/* 138 */             Player target = Bukkit.getPlayer(args[1]);
/* 139 */             String typeItem = getConfig().getString("type-of-customItem");
/* 140 */             typeItem = typeItem.toUpperCase();
/* 141 */             if (Material.getMaterial(typeItem) != null) {
/* 142 */               ItemStack item = new ItemStack(Material.getMaterial(typeItem), Integer.parseInt(args[2]));
/* 143 */               ItemMeta itemMeta = item.getItemMeta();
/* 144 */               String lore = getConfig().getString("lore-of-customItem");
/* 145 */               lore = lore.replaceAll("&", "§");
/* 146 */               itemMeta.setDisplayName("§cFearsight I");
/* 147 */               itemMeta.setLore(Arrays.asList(new String[] { lore }));
/* 148 */               item.setItemMeta(itemMeta);
/* 149 */               PlayerInventory inv = target.getInventory();
/* 150 */               inv.addItem(new ItemStack[] { item });
/* 151 */               return true;
/*     */             }
/*     */ 
/* 154 */             Bukkit.broadcastMessage("§cInvalid item type.");
/*     */           }
/*     */           else {
/* 157 */             Bukkit.broadcastMessage("§cInvalid player.");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 162 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\AtlasEnchants (1).jar
 * Qualified Name:     com.mralby.atlasenchants.Main
 * JD-Core Version:    0.6.2
 */