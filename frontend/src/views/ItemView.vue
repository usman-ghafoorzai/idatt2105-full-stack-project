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
            <div id="image-seller-information">
               <ItemPicture
                :images="['src/assets/images/boat.jpg', 'src/assets/images/Thor.png', 'src/assets/images/Myfavoritepokemons.png','src/assets/images/boat.jpg']"
                id="item-picture"
                />
                <SellerInfo
                    :name="'John Doe'"
                    :phoneNumber="'12345678'"
                    :email="'makhmadfranav@gmail.com'"
                /> 
            </div>
            
        </div>
        
        <div id="right-section">
            <div id="item-description-buttons">
                <ItemDescription
                    title="Bayliner VR 5 Cuddy OB Lite brukt"
                    price="10000"
                    description="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
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
    </div>
</template>

<style scoped>
    #item-view-container {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: flex-start;
        gap: 150px; /* Space between left and right sections */
        width: 100%;
        max-width: 1500px; /* Limit the maximum width of the container */
        margin: 0 auto; /* Center the container horizontally */
        padding: 20px;
        box-sizing: border-box;
    }
    #left-section {
        display: flex;
        flex: 1;
        flex-direction: column;
        gap: 20px;
    }
    #right-section {
        display: flex;
        flex: 1;
        flex-direction: column;
        gap: 20px;
    }
    #item-description-buttons {
        display: grid;
        grid-template-rows: auto auto;
        gap: 20px;
        width: auto;
        margin-right: 10%;
    }
    #image-seller-information {
        display: flex;
        flex-direction: column;
        gap: 40px;
    }
    #buttons-container {
        display: flex;
        flex-direction: row;
        gap: 20px; /* Space between the buttons */
        justify-content: flex-start; /* Align buttons to the left */
    }
</style>