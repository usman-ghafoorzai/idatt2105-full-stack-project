<script setup>
    import { ref, onMounted } from 'vue';
    import ItemPicture from '../components/ItemPicture.vue';
    import ItemDescription from '../components/ItemDescription.vue';
    import SellerInfo from '../components/SellerInfo.vue';
    import ReserveButton from '../components/ReserveButton.vue';
    import BuyNowButton from '../components/BuyNowButton.vue';
</script>

<template>
    <div id="item-view-container">
        <div id="left-section">
            <ItemPicture
                :images="['src/assets/images/boat.jpg', 'src/assets/images/Thor.png', 'src/assets/images/My favorite pokemons.png']"
                id="item-picture"
            />
            <SellerInfo
                :name="'John Doe'"
                :phoneNumber="'12345678'"
                :email="'makhmadfranav@gmail.com'"
            />
        </div>
        
        <div id="right-section">
            <ItemDescription
                title="Bayliner VR 5 Cuddy OB Lite brukt"
                price="10000"
                description="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
                image="src/assets/images/boat.jpg"
                location="Oslo"
                :categories="['Boat', 'Used']"
            ></ItemDescription>
            
            <div id="buttons-container">
                <ReserveButton></ReserveButton>
                <BuyNowButton></BuyNowButton>
            </div>
        </div>
    </div>
</template>

<style scoped>
    #item-view-container {
        display: flex;
        flex-direction: row;
        width: 100%;
        gap: 100px;
    }
    #left-section {
        display: flex;
        flex: 1;
        flex-direction: column;
        padding: 20px;
        gap: 20px;
    }
    #right-section {
        display: flex;
        flex: 2;
        flex-direction: column;
        padding: 20px;
        gap: 20px;
    }
    
</style>