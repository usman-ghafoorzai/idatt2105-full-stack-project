import { describe, it, expect, vi, beforeEach } from 'vitest';
import { mount } from '@vue/test-utils';
import CategoryPopup from '../components/CategoryPopup.vue';
import { createTestingPinia } from '@pinia/testing';
import { useCategoryStore } from '../stores/CategoryStore.js';
import { useSearchStore } from '../stores/SearchStore.js';

describe('CategoryPopup.vue', () => {
  let wrapper;
  let categoryStore;
  let searchStore;

  beforeEach(() => {
    wrapper = mount(CategoryPopup, {
      global: {
        plugins: [
          createTestingPinia({
            stubActions: false,
            createSpy: vi.fn, // Use vi.fn() for mocking actions
          }),
        ],
      },
    });

    // Access the mocked stores
    categoryStore = useCategoryStore();
    searchStore = useSearchStore();
  });

  it('renders the component', () => {
    const popup = wrapper.find('.popup');
    const title = wrapper.find('h2');
    expect(popup.exists()).toBe(true);
    expect(title.text()).toBe('Categories');
  });

  it('renders categories when mocked data is provided', async () => {
    // Mock the categories in the store
    categoryStore.categories = [
      { id: 1, name: 'Category 1', parentCategory: null },
      { id: 2, name: 'Category 2', parentCategory: null },
    ];

    // Trigger the fetchCategories method
    await wrapper.vm.fetchCategories();
    await wrapper.vm.$nextTick();

    // Assert that the categories are rendered
    const categoryItems = wrapper.findAll('li');
    expect(categoryItems.length).toBe(2);
    expect(categoryItems[0].text()).toContain('Category 1');
    expect(categoryItems[1].text()).toContain('Category 2');
  });

  it('emits close event when close button is clicked', async () => {
    const closeButton = wrapper.find('.close-button');
    await closeButton.trigger('click');
    expect(wrapper.emitted().close).toBeTruthy();
  });
});