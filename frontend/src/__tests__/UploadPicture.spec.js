import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest';
import { mount } from '@vue/test-utils';
import UploadPicture from '../components/UploadPicture.vue';
import genericAvatar from '../assets/generic-avatar.png';

describe('UploadPicture.vue', () => {
  let wrapper;

  // Before each test, mount the component and stub the URL.createObjectURL method
  beforeEach(() => {
    wrapper = mount(UploadPicture);
    // Mock the URL.createObjectURL function to return a predictable URL for testing
    vi.stubGlobal('URL', { createObjectURL: vi.fn(() => 'blob:mock-url') });
  });

  // After each test, clean up the stubbed globals to avoid affecting other tests
  afterEach(() => {
    vi.unstubAllGlobals();
  });

  // Test if the component initially renders with the default generic avatar
  it('renders the default generic avatar', () => {
    const img = wrapper.find('#profile-pic');
    // Verify image element exists in DOM
    expect(img.exists()).toBe(true);
    // Verify image src is set to the imported generic avatar
    expect(img.attributes('src')).toBe(genericAvatar);
  });

  // Test if clicking the image container triggers the file input click function
  it('triggers file input when image is clicked', async () => {
    // Get the file input element
    const fileInput = wrapper.find('input[type="file"]').element;
    
    // Spy on the click event of the input element
    const mockClick = vi.fn();
    fileInput.click = mockClick;  // Directly mock the click function

    // Trigger click on the image container (which should trigger file input click)
    const imageContainer = wrapper.find('.image-container');
    await imageContainer.trigger('click');

    // Verify the file input click was triggered
    expect(mockClick).toHaveBeenCalled();
  });

  // Test if selecting a file updates the profile picture
  it('updates the profile picture when a file is selected', async () => {
    // Create a mock file object
    const mockFile = new File(['dummy'], 'avatar.png', { type: 'image/png' });

    // Get the file input element
    const input = wrapper.find('input[type="file"]');

    // Simulate file selection
    await input.setFiles([mockFile]);

    // Wait for the next tick to allow Vue to process the update
    await wrapper.vm.$nextTick();

    // Verify image src is updated to the blob URL from our mocked createObjectURL
    const updatedImg = wrapper.find('#profile-pic');
    expect(updatedImg.attributes('src')).toBe('blob:mock-url');
  });
});
