<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();
</script>

<template>
  <div class="home-content">
    <!-- Hero Section -->
    <div class="hero-section">
      <h1>Welcome to our marketplace</h1>
      <p>Find what you need, sell what you don't</p>
      <div class="hero-buttons">
        <button class="btn primary-btn" @click="router.push('/search')">Browse Items</button>
        <button class="btn primary-btn" @click="router.push('/sell')">Sell an Item</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.home-content {
  padding: 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
}

.hero-section {
  text-align: center;
  margin-bottom: 40px;
  padding: 60px 20px;
  background-color: var(--color-salmon);
  border-radius: 10px;
  width: 100%;
}

.hero-section h1 {
  font-size: 2.5rem;
  margin-bottom: 16px;
  color: #333;
}

.hero-section p {
  font-size: 1.2rem;
  margin-bottom: 30px;
  color: #242424;
}

.hero-buttons {
  display: flex;
  gap: 20px;
  justify-content: center;
}

.btn {
  padding: 12px 24px;
  border-radius: 6px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  border: none;
}

.primary-btn {
  background-color: var(--color-red);
  color: white;
}

.primary-btn:hover {
  background-color: #e57373;
}

@media (max-width: 768px) {
  .hero-section h1 {
    font-size: 2rem;
  }
  .hero-section p {
    font-size: 1rem;
  }
}

@media (max-width: 480px) {
  .hero-buttons {
    flex-direction: column;
    gap: 15px;
  }
}
</style>
