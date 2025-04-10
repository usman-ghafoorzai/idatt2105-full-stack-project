<script setup>
    import { ref , onMounted } from 'vue';
    import ItemPicture from '../components/ItemPicture.vue';
    import ItemDescription from '../components/ItemDescription.vue';
    import SellerInfo from '../components/SellerInfo.vue';
    import ReserveButton from '../components/ReserveButton.vue';
    import BuyNowButton from '../components/BuyNowButton.vue';
    import { useItemStore } from '../stores/ItemStore.js';
    import { useSellerInformationStore } from '../stores/SellerInformationStore.js';
    import { getAddress } from '@/utils/reverseGeoLocation';
    import PopUpModal from '../components/PopUpModal.vue';
    import { getItemImages } from '../api/itemAPI.js';
    import { getLoggedInUser } from '../api/userAPI.js';
    import { reserveItem } from '../api/reservationAPI.js';

    const itemStore = useItemStore();
    const sellerStore = useSellerInformationStore();
    const item = itemStore.selectedItem;
    const sellerInformation = sellerStore.sellerInformation;

    const address = ref('');
    const modalTitle = ref('');
    const modalRef = ref(null);
    const modalButtonText = ref('Confirm');

    function openBuyNowModal() {
    modalTitle.value = 'Confirm purchase';
    modalButtonText.value = 'Confirm';
    modalRef.value.openModal();
    }

    function openReserveModal() {
    modalTitle.value = 'Reserve Item';
    modalButtonText.value = 'Reserve';
    modalRef.value.openModal();
    }

    async function confirmAction() {
    if (modalTitle.value.includes('Reserve')) {
        try {
            const user = await getLoggedInUser();
            console.log(user);
            await reserveItem(user.id, item.id);
            alert("Item reserved successfully!");
            modalRef.value.closeModal();
            } catch (error) {
            console.error("Reservation failed:", error);
            alert("Failed to reserve item. Please make sure you're logged in.");
        }
    } else if (modalTitle.value.includes('purchase')) {
            // Handle purchase confirmation
            console.log("Purchase confirmed");
            modalRef.value.closeModal();
    }
    }
    const images = ref([]);

    onMounted(async () => {
        try {
            address.value = await getAddress(item.locationLatitude, item.locationLongitude);
            images.value = await getItemImages(item.id);
        } catch (error) {
            console.error('Error fetching item data:', error);
        }
    });
</script>

<template>
    <div id="item-view-container">
        <div id="left-section">
            <div id="image-seller-information">
               <ItemPicture
                :images="images"
                id="item-picture"
                />
                <SellerInfo
                    :name="sellerStore.sellerInformation.firstName + ' ' + sellerStore.sellerInformation.lastName + ' (' + sellerStore.sellerInformation.username + ')'"
                    :phoneNumber="'12345678'"
                    :email="sellerInformation.email"
                /> 
            </div>
            
        </div>
        
        <div id="right-section">
            <div id="item-description-buttons">
                <ItemDescription
                    :title="item.title"
                    :price=item.price
                    :description="item.description"
                    :image="images[0]"
                    :location="address"
                    :categories="item.categories"
                ></ItemDescription>
                
                <div id="buttons-container">
                    <BuyNowButton @click="openBuyNowModal"></BuyNowButton>
                    <ReserveButton @click="openReserveModal"></ReserveButton>
                </div>
            </div>
        </div>

    </div>

    <PopUpModal
      ref="modalRef"
      :title="modalTitle"
      :buttonText="modalButtonText"
      :onConfirm="confirmAction"
    />
</template>

<style scoped>
#item-view-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: flex-start;
  gap: 150px;
  width: 100%;
  max-width: 1500px;
  margin: 0 auto;
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
  gap: 20px;
  justify-content: flex-start;
}

@media (max-width: 768px) {
  #item-view-container {
    flex-direction: column; /* Stack components vertically */
    gap: 20px;
    align-items: center;
  }
}
</style>
