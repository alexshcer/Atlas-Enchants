/*     */ package com.mralby.atlasenchants;
/*     */ 
/*     */ import com.codingforcookies.armorequip.ArmorEquipEvent;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.block.Chest;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Ambient;
/*     */ import org.bukkit.entity.Animals;
/*     */ import org.bukkit.entity.Boss;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Flying;
/*     */ import org.bukkit.entity.Monster;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Slime;
/*     */ import org.bukkit.entity.Villager;
/*     */ import org.bukkit.entity.WaterMob;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.inventory.InventoryAction;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.inventory.InventoryType.SlotType;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.event.world.ChunkPopulateEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ import org.inventivetalent.glow.GlowAPI;
/*     */ import org.inventivetalent.glow.GlowAPI.Color;
/*     */ 
/*     */ public class Events
/*     */   implements Listener
/*     */ {
/*     */   private Main plugin;
/*     */ 
/*     */   public Events(Main pl)
/*     */   {
/*  46 */     this.plugin = pl;
/*     */   }
/*     */ 
/*     */   public boolean isHelmet(Material type)
/*     */   {
/*  51 */     if ((type == Material.LEATHER_HELMET) || 
/*  52 */       (type == Material.IRON_HELMET) || 
/*  53 */       (type == Material.CHAINMAIL_HELMET) || 
/*  54 */       (type == Material.GOLDEN_HELMET) || 
/*  55 */       (type == Material.DIAMOND_HELMET) || 
/*  56 */       (type == Material.NETHERITE_HELMET) || 
/*  57 */       (type == Material.TURTLE_HELMET)) {
/*  58 */       return true;
/*     */     }
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean hasCustomEnchant(ItemStack helmet) {
/*  64 */     ItemMeta helmetMeta = helmet.getItemMeta();
/*  65 */     String enchantName = "§cFearsight I";
/*  66 */     if (helmetMeta.hasLore()) {
/*  67 */       for (String lore : helmetMeta.getLore()) {
/*  68 */         if ((lore.equals(enchantName)) || (lore.equals(enchantName + "I")) || (lore.equals(enchantName + "II"))) {
/*  69 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void onMove(PlayerMoveEvent event) {
/*  78 */     final Player player = event.getPlayer();
/*  79 */     if (this.plugin.hasHelmet.containsKey(player)) {
/*  80 */       if (this.plugin.ColorTask.containsKey(player)) {
/*  81 */         Bukkit.getScheduler().cancelTask(((BukkitTask)this.plugin.ColorTask.get(player)).getTaskId());
/*  82 */         this.plugin.ColorTask.remove(player);
/*     */       }
/*  84 */       ItemStack helmet = player.getInventory().getHelmet();
/*  85 */       ItemMeta helmetMeta = helmet.getItemMeta();
/*  86 */       String enchantName = "§cFearsight I";
/*  87 */       int level = 0;
/*     */ 
/*  89 */       List listE = null;
/*  90 */       for (String a : helmetMeta.getLore()) {
/*  91 */         if (a.equals(enchantName)) {
/*  92 */           level = 1;
/*  93 */           listE = player.getNearbyEntities(this.plugin.getConfig().getInt("radius-of-glowing-1"), this.plugin.getConfig().getInt("radius-of-glowing-1"), this.plugin.getConfig().getInt("radius-of-glowing-1"));
/*     */         }
/*  95 */         else if (a.equals(enchantName + "I")) {
/*  96 */           level = 2;
/*  97 */           listE = player.getNearbyEntities(this.plugin.getConfig().getInt("radius-of-glowing-2"), this.plugin.getConfig().getInt("radius-of-glowing-2"), this.plugin.getConfig().getInt("radius-of-glowing-2"));
/*     */         }
/*  99 */         else if (a.equals(enchantName + "II")) {
/* 100 */           level = 3;
/* 101 */           listE = player.getNearbyEntities(this.plugin.getConfig().getInt("radius-of-glowing-3"), this.plugin.getConfig().getInt("radius-of-glowing-3"), this.plugin.getConfig().getInt("radius-of-glowing-3"));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 106 */       for (Entity e : listE)
/*     */       {
/* 108 */         if (((e instanceof Monster)) || ((e instanceof Flying)) || ((e instanceof Slime)) || ((e instanceof Boss))) {
/* 109 */           GlowAPI.setGlowing(e, GlowAPI.Color.DARK_RED, player);
/*     */         }
/* 111 */         else if (((e instanceof Animals)) || ((e instanceof Ambient)) || ((e instanceof WaterMob))) {
/* 112 */           GlowAPI.setGlowing(e, GlowAPI.Color.DARK_GREEN, player);
/*     */         }
/* 114 */         else if (((e instanceof Player)) || ((e instanceof Villager))) {
/* 115 */           GlowAPI.setGlowing(e, GlowAPI.Color.WHITE, player);
/*     */         }
/* 117 */         if (e.getLocation().distance(player.getLocation()) >= this.plugin.getConfig().getInt("radius-of-glowing-" + level)) {
/* 118 */           GlowAPI.setGlowing(e, false, player);
/*     */         }
/*     */       }
/* 121 */       if (this.plugin.playerEntities.get(player) != listE) {
/* 122 */         this.plugin.playerEntities.put(player, listE);
/*     */       }
/*     */     }
/* 125 */     else if (!this.plugin.ColorTask.containsKey(player)) {
/* 126 */       this.plugin.ColorTask.put(player, new BukkitRunnable()
/*     */       {
/*     */         public void run() {
/* 129 */           for (Entity e : player.getNearbyEntities(55.0D, 55.0D, 55.0D))
/*     */           {
/* 131 */             GlowAPI.setGlowing(e, false, player);
/*     */           }
/*     */         }
/*     */       }
/* 134 */       .runTaskTimer(this.plugin, 0L, 15L));
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void onDeath(PlayerDeathEvent event)
/*     */   {
/* 141 */     Player player = event.getEntity();
/* 142 */     for (Entity e : player.getNearbyEntities(50.0D, 50.0D, 50.0D))
/* 143 */       if (GlowAPI.isGlowing(e, player))
/* 144 */         GlowAPI.setGlowing(e, false, player);
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void onQuit(PlayerQuitEvent event)
/*     */   {
/* 151 */     Player player = event.getPlayer();
/* 152 */     if (this.plugin.ColorTask.containsKey(player)) {
/* 153 */       Bukkit.getScheduler().cancelTask(((BukkitTask)this.plugin.ColorTask.get(player)).getTaskId());
/* 154 */       this.plugin.ColorTask.remove(player);
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void onJoin(PlayerJoinEvent event) {
/* 160 */     Player player = event.getPlayer();
/* 161 */     if ((player.getInventory().getHelmet() != null) && 
/* 162 */       (isHelmet(player.getInventory().getHelmet().getType())) && (hasCustomEnchant(player.getInventory().getHelmet())) && 
/* 163 */       (!this.plugin.hasHelmet.containsKey(player)))
/* 164 */       this.plugin.hasHelmet.put(player, Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void onArmor(ArmorEquipEvent event)
/*     */   {
/* 173 */     Player player = event.getPlayer();
/*     */ 
/* 177 */     if ((event.getNewArmorPiece() != null) && (event.getNewArmorPiece().getType() != Material.AIR)) {
/* 178 */       if (event.getNewArmorPiece().getItemMeta().hasLore()) {
/* 179 */         if ((player.getInventory().getHelmet() != null) && (player.getInventory().getHelmet().getType() != Material.AIR) && (!isHelmet(event.getNewArmorPiece().getType())) && (!hasCustomEnchant(event.getNewArmorPiece()))) {
/* 180 */           event.setCancelled(true);
/*     */         }
/* 183 */         else if (!this.plugin.hasHelmet.containsKey(player)) {
/* 184 */           this.plugin.hasHelmet.put(player, Boolean.valueOf(true));
/*     */         }
/*     */       }
/*     */     }
/* 188 */     else if ((event.getOldArmorPiece() != null) && (event.getOldArmorPiece().getType() != Material.AIR) && 
/* 189 */       (event.getOldArmorPiece().getItemMeta().hasLore()) && 
/* 190 */       (hasCustomEnchant(event.getOldArmorPiece()))) {
/* 191 */       if (this.plugin.hasHelmet.containsKey(player)) {
/* 192 */         this.plugin.hasHelmet.remove(player);
/*     */       }
/* 194 */       if (this.plugin.playerEntities.containsKey(player)) {
/* 195 */         for (Entity e : (List)this.plugin.playerEntities.get(player)) {
/* 196 */           if (GlowAPI.isGlowing(e, player)) {
/* 197 */             GlowAPI.setGlowing(e, false, player);
/*     */           }
/*     */         }
/*     */       }
/* 201 */       if (this.plugin.playerEntities.containsKey(player))
/* 202 */         this.plugin.playerEntities.remove(player);
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void onChunkGen(ChunkPopulateEvent event)
/*     */   {
/* 212 */     if ((event.getWorld().equals(Bukkit.getServer().getWorld("world"))) && (Bukkit.getServer().getOnlinePlayers().size() > 0)) {
/* 213 */       Random r = new Random();
/* 214 */       for (BlockState state : event.getChunk().getTileEntities())
/* 215 */         if (state.getType() == Material.CHEST)
/* 216 */           switch (r.nextInt(3) + 1) {
/*     */           case 1:
/* 218 */             int perc1 = r.nextInt(100) + 1;
/* 219 */             if (perc1 <= this.plugin.getConfig().getInt("perc-random-enchant-1")) {
/* 220 */               String typeItem = this.plugin.getConfig().getString("type-of-customItem");
/* 221 */               typeItem = typeItem.toUpperCase();
/* 222 */               String name = "§cFearsight I";
/* 223 */               String lore = this.plugin.getConfig().getString("lore-of-customItem");
/* 224 */               lore = lore.replaceAll("&", "§");
/* 225 */               ItemStack item = new ItemStack(Material.getMaterial(typeItem));
/* 226 */               ItemMeta itemMeta = item.getItemMeta();
/* 227 */               itemMeta.setDisplayName(name);
/* 228 */               itemMeta.setLore(Arrays.asList(new String[] { lore }));
/* 229 */               item.setItemMeta(itemMeta);
/*     */ 
/* 231 */               Chest chest = (Chest)state;
/* 232 */               chest.getBlockInventory().addItem(new ItemStack[] { item });
/*     */             }
/* 234 */             break;
/*     */           case 2:
/* 236 */             int perc2 = r.nextInt(100) + 1;
/* 237 */             if (perc2 <= this.plugin.getConfig().getInt("perc-random-enchant-2")) {
/* 238 */               String typeItem = this.plugin.getConfig().getString("type-of-customItem");
/* 239 */               typeItem = typeItem.toUpperCase();
/* 240 */               String name = "§cFearsight II";
/* 241 */               String lore = this.plugin.getConfig().getString("lore-of-customItem");
/* 242 */               lore = lore.replaceAll("&", "§");
/* 243 */               ItemStack item = new ItemStack(Material.getMaterial(typeItem));
/* 244 */               ItemMeta itemMeta = item.getItemMeta();
/* 245 */               itemMeta.setDisplayName(name);
/* 246 */               itemMeta.setLore(Arrays.asList(new String[] { lore }));
/* 247 */               item.setItemMeta(itemMeta);
/*     */ 
/* 249 */               Chest chest = (Chest)state;
/* 250 */               chest.getBlockInventory().addItem(new ItemStack[] { item });
/*     */             }
/* 252 */             break;
/*     */           case 3:
/* 254 */             int perc3 = r.nextInt(100) + 1;
/* 255 */             if (perc3 <= this.plugin.getConfig().getInt("perc-random-enchant-3")) {
/* 256 */               String typeItem = this.plugin.getConfig().getString("type-of-customItem");
/* 257 */               typeItem = typeItem.toUpperCase();
/* 258 */               String name = "§cFearsight III";
/* 259 */               String lore = this.plugin.getConfig().getString("lore-of-customItem");
/* 260 */               lore = lore.replaceAll("&", "§");
/* 261 */               ItemStack item = new ItemStack(Material.getMaterial(typeItem));
/* 262 */               ItemMeta itemMeta = item.getItemMeta();
/* 263 */               itemMeta.setDisplayName(name);
/* 264 */               itemMeta.setLore(Arrays.asList(new String[] { lore }));
/* 265 */               item.setItemMeta(itemMeta);
/*     */ 
/* 267 */               Chest chest = (Chest)state;
/* 268 */               chest.getBlockInventory().addItem(new ItemStack[] { item });
/*     */             }
/*     */             break;
/*     */           }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void dragOnItemToEnchant(InventoryClickEvent event)
/*     */   {
/* 283 */     if (event.getCurrentItem() == null) {
/* 284 */       return;
/*     */     }
/*     */ 
/* 287 */     ItemStack slot = event.getCurrentItem();
/* 288 */     ItemMeta slotMeta = slot.getItemMeta();
/* 289 */     ItemStack cursore = event.getCursor();
/*     */ 
/* 291 */     Player player = (Player)event.getWhoClicked();
/* 292 */     String nameCursor = "§cFearsight I";
/* 293 */     String[] parts = nameCursor.split(" ");
/* 294 */     String name = parts[0];
/* 295 */     String enchantLevel = parts[1];
/*     */ 
/* 297 */     if ((event.getAction() == InventoryAction.SWAP_WITH_CURSOR) && (slot != null) && (cursore != null) && (isHelmet(slot.getType())) && 
/* 298 */       (cursore.getItemMeta().getDisplayName().contains(name)) && (cursore.getItemMeta().hasLore()) && (event.getSlotType() != InventoryType.SlotType.ARMOR))
/* 299 */       if (cursore.getAmount() == 1)
/*     */       {
/* 301 */         if (isHelmet(slot.getType())) {
/* 302 */           if (slotMeta.hasLore()) {
/* 303 */             List l = slotMeta.getLore();
/* 304 */             for (String row : l) {
/* 305 */               if (row.equals(nameCursor)) {
/* 306 */                 l.set(l.indexOf(row), cursore.getItemMeta().getDisplayName());
/*     */               }
/* 308 */               else if (row.equals(nameCursor + "I")) {
/* 309 */                 l.set(l.indexOf(row), cursore.getItemMeta().getDisplayName());
/*     */               }
/* 311 */               else if (row.equals(nameCursor + "II")) {
/* 312 */                 String m = this.plugin.getConfig().getString("max-enchant-message");
/* 313 */                 m = m.replaceAll("&", "§");
/* 314 */                 player.sendMessage(m);
/* 315 */                 player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
/* 316 */                 event.setCancelled(true);
/*     */               }
/*     */             }
/* 319 */             slotMeta.setLore(l);
/*     */           }
/*     */           else {
/* 322 */             slotMeta.setLore(Arrays.asList(new String[] { cursore.getItemMeta().getDisplayName() }));
/*     */           }
/*     */ 
/* 325 */           if (!event.isCancelled()) {
/* 326 */             player.setItemOnCursor(new ItemStack(Material.AIR));
/*     */           }
/* 328 */           slot.setItemMeta(slotMeta);
/* 329 */           event.setCurrentItem(slot);
/*     */         }
/*     */ 
/* 333 */         event.setCancelled(true);
/*     */       }
/*     */       else {
/* 336 */         String m = this.plugin.getConfig().getString("enchant-error-message");
/* 337 */         m = m.replaceAll("&", "§");
/* 338 */         player.sendMessage(m);
/* 339 */         player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
/* 340 */         event.setCancelled(true);
/*     */       }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void clickGUI(InventoryClickEvent event)
/*     */   {
/* 349 */     if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + ChatColor.BOLD + "Blacksmith"))
/*     */     {
/* 351 */       Player player = (Player)event.getWhoClicked();
/*     */ 
/* 353 */       if (event.getCurrentItem() == null) {
/* 354 */         return;
/*     */       }
/* 356 */       if ((event.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE) || (event.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE)) {
/* 357 */         event.setCancelled(true);
/*     */       }
/* 359 */       else if ((event.getCurrentItem().getType() == Material.LIME_STAINED_GLASS_PANE) && (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lUpgrade")))
/* 360 */         if ((event.getInventory().getItem(11) == null) || (event.getInventory().getItem(15) == null)) {
/* 361 */           event.setCancelled(true);
/*     */         }
/*     */         else {
/* 364 */           String nameCustomItem = "§cFearsight I";
/* 365 */           String[] parts = nameCustomItem.split(" ");
/* 366 */           String name = parts[0];
/* 367 */           String enchantLevel = parts[1];
/* 368 */           String typeItem = this.plugin.getConfig().getString("type-of-customItem");
/* 369 */           typeItem = typeItem.toUpperCase();
/* 370 */           if ((event.getInventory().getItem(11).getType() == Material.getMaterial(typeItem)) && (event.getInventory().getItem(15).getType() == Material.getMaterial(typeItem)) && (event.getInventory().getItem(11).getItemMeta().getDisplayName().equals(event.getInventory().getItem(15).getItemMeta().getDisplayName()))) {
/* 371 */             if ((event.getInventory().getItem(11).getAmount() == 1) && (event.getInventory().getItem(15).getAmount() == 1)) {
/* 372 */               String fullmessage = this.plugin.getConfig().getString("inventory-full-message-onopen");
/* 373 */               fullmessage = fullmessage.replaceAll("&", "§");
/* 374 */               if (player.getInventory().firstEmpty() != -1) {
/* 375 */                 if (this.plugin.getEmptySlots(player) >= 2) {
/* 376 */                   ItemStack slot1 = event.getInventory().getItem(11);
/* 377 */                   ItemMeta slotMeta1 = slot1.getItemMeta();
/* 378 */                   ItemStack slot2 = event.getInventory().getItem(15);
/* 379 */                   ItemMeta slotMeta2 = slot2.getItemMeta();
/* 380 */                   ItemStack vuoto = new ItemStack(Material.AIR);
/*     */ 
/* 382 */                   if ((slotMeta1.hasLore()) && (slotMeta2.hasLore())) {
/* 383 */                     int nDiamond = 0;
/* 384 */                     for (ItemStack is : player.getInventory().getContents()) {
/* 385 */                       if ((is != null) && 
/* 386 */                         (is.getType() == Material.DIAMOND)) {
/* 387 */                         nDiamond += is.getAmount();
/*     */                       }
/*     */                     }
/*     */ 
/* 391 */                     if ((slotMeta1.getDisplayName().equals(nameCustomItem)) && (slotMeta2.getDisplayName().equals(nameCustomItem))) {
/* 392 */                       if (nDiamond >= this.plugin.getConfig().getInt("amount-of-diamonds-to-level-2")) {
/* 393 */                         slotMeta1.setDisplayName(slotMeta1.getDisplayName() + "I");
/* 394 */                         player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
/* 395 */                         player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.DIAMOND, this.plugin.getConfig().getInt("amount-of-diamonds-to-level-2")) });
/*     */                       }
/*     */                       else {
/* 398 */                         String nodiamond = this.plugin.getConfig().getString("no-diamond");
/* 399 */                         nodiamond = nodiamond.replaceAll("&", "§");
/* 400 */                         player.sendMessage(nodiamond);
/* 401 */                         player.sendMessage("§cYou need " + this.plugin.getConfig().getInt("amount-of-diamonds-to-level-2") + " diamonds.");
/* 402 */                         player.getInventory().addItem(new ItemStack[] { slot2 });
/* 403 */                         player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
/* 404 */                         event.setCancelled(true);
/*     */                       }
/*     */                     }
/* 407 */                     else if ((slotMeta1.getDisplayName().equals(nameCustomItem + "I")) && (slotMeta2.getDisplayName().equals(nameCustomItem + "I"))) {
/* 408 */                       if (nDiamond >= this.plugin.getConfig().getInt("amount-of-diamonds-to-level-3")) {
/* 409 */                         slotMeta1.setDisplayName(slotMeta1.getDisplayName() + "I");
/* 410 */                         player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
/* 411 */                         player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.DIAMOND, this.plugin.getConfig().getInt("amount-of-diamonds-to-level-3")) });
/*     */                       }
/*     */                       else {
/* 414 */                         String nodiamond = this.plugin.getConfig().getString("no-diamond");
/* 415 */                         nodiamond = nodiamond.replaceAll("&", "§");
/* 416 */                         player.sendMessage(nodiamond);
/* 417 */                         player.sendMessage("§cYou need " + this.plugin.getConfig().getInt("amount-of-diamonds-to-level-3") + " diamonds.");
/* 418 */                         player.getInventory().addItem(new ItemStack[] { slot2 });
/* 419 */                         player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
/* 420 */                         event.setCancelled(true);
/*     */                       }
/*     */                     }
/* 423 */                     else if ((slotMeta1.getDisplayName().equals(nameCustomItem + "II")) && (slotMeta2.getDisplayName().equals(nameCustomItem + "II"))) {
/* 424 */                       String m = this.plugin.getConfig().getString("max-enchant-message");
/* 425 */                       m = m.replaceAll("&", "§");
/* 426 */                       player.sendMessage(m);
/* 427 */                       player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
/* 428 */                       player.getInventory().addItem(new ItemStack[] { slot2 });
/* 429 */                       event.setCancelled(true);
/*     */                     }
/* 431 */                     event.getInventory().setItem(11, vuoto);
/* 432 */                     event.getInventory().setItem(15, vuoto);
/* 433 */                     slot1.setItemMeta(slotMeta1);
/* 434 */                     player.getInventory().addItem(new ItemStack[] { slot1 });
/* 435 */                     event.setCancelled(true);
/*     */                   }
/*     */                 }
/*     */                 else {
/* 439 */                   player.sendMessage(fullmessage);
/* 440 */                   player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
/* 441 */                   event.setCancelled(true);
/*     */                 }
/*     */               }
/*     */               else {
/* 445 */                 player.sendMessage(fullmessage);
/* 446 */                 player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
/* 447 */                 event.setCancelled(true);
/*     */               }
/*     */             }
/*     */             else {
/* 451 */               String m = this.plugin.getConfig().getString("enchant-error-message");
/* 452 */               m = m.replaceAll("&", "§");
/* 453 */               player.sendMessage(m);
/* 454 */               player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
/* 455 */               event.setCancelled(true);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 460 */             event.setCancelled(true);
/*     */           }
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void playercloseGUI(InventoryCloseEvent event)
/*     */   {
/* 469 */     Player player = (Player)event.getPlayer();
/* 470 */     if ((player.isOnline()) && (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + ChatColor.BOLD + "Blacksmith")))
/* 471 */       if ((event.getInventory().getItem(11) != null) || (event.getInventory().getItem(15) != null)) {
/* 472 */         String fullmessage = this.plugin.getConfig().getString("inventory-full-message-onclose");
/* 473 */         fullmessage = fullmessage.replaceAll("&", "§");
/* 474 */         if (player.getInventory().firstEmpty() != -1) {
/* 475 */           if (this.plugin.getEmptySlots(player) >= 2) {
/* 476 */             if (event.getInventory().getItem(11) == null) {
/* 477 */               player.getInventory().addItem(new ItemStack[] { event.getInventory().getItem(15) });
/* 478 */               event.getInventory().clear();
/*     */             }
/* 480 */             else if (event.getInventory().getItem(15) == null) {
/* 481 */               player.getInventory().addItem(new ItemStack[] { event.getInventory().getItem(11) });
/* 482 */               event.getInventory().clear();
/*     */             }
/*     */             else {
/* 485 */               player.getInventory().addItem(new ItemStack[] { event.getInventory().getItem(11) });
/* 486 */               player.getInventory().addItem(new ItemStack[] { event.getInventory().getItem(15) });
/* 487 */               event.getInventory().clear();
/*     */             }
/*     */           }
/*     */           else {
/* 491 */             player.getWorld().dropItem(player.getLocation(), event.getInventory().getItem(11));
/* 492 */             player.getWorld().dropItem(player.getLocation(), event.getInventory().getItem(15));
/* 493 */             player.sendMessage(fullmessage);
/* 494 */             player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
/*     */           }
/*     */         }
/*     */         else {
/* 498 */           player.getWorld().dropItem(player.getLocation(), event.getInventory().getItem(11));
/* 499 */           player.getWorld().dropItem(player.getLocation(), event.getInventory().getItem(15));
/* 500 */           player.sendMessage(fullmessage);
/* 501 */           player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
/*     */         }
/*     */       }
/*     */       else;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\AtlasEnchants (1).jar
 * Qualified Name:     com.mralby.atlasenchants.Events
 * JD-Core Version:    0.6.2
 */