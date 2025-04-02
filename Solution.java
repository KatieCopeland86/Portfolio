
/**
 * Write a description of class Solution here.
 *
 * @author Katie Copeland
 * @version Fall2024
 */
public class Solution
{
    private int left;
    private int right;
    public Solution()
    {
        left = 0;
        right = 0;
    }
    
    public int trap(int[] height) {
        //following constraint
        int n = height.length;
        int[] leftMax = new int[n];  // Max height to the left of each bar
        int[] rightMax = new int[n]; // Max height to the right of each bar
        int trappedWater = 0; // trapped water
        
        //if empty meaning no water can be trapped 
        if(n == 0)
        {
            return 0;
        }

        
        //fill left array, first bar cannot trap water
        //initialize leftMax with the first index from the parameter
        leftMax[0] = height[0];
        for(int i = 1; i < height.length; i++)
        {
            //max height to the left
            leftMax[i] = MaxHeight(leftMax[i-1], height[i]);
        }
        
        //fill right array, last bar cannot trap water
        //initialize last index of rightMax with n-1 
        rightMax[n-1] = height[n-1];
        for(int i = height.length - 2; i >= 0; i--)
        {
            //max height to the right
            rightMax[i] = MaxHeight(rightMax[i+1], height[i]);
        }
        
        //Calculation for trapped water
        for(int i =0; i < height.length; i++)
        {
            trappedWater += MinHeight(leftMax[i], rightMax[i]) - height[i];
        }
        
        
        return trappedWater;
    }
    
    //method to compare max height
    public int MaxHeight(int left, int right)
    {
        this.left = left;
        this.right = right;
        return (this.left > this.right)?this.left: this.right;
    }
    
    //method to compare min height
    public int MinHeight(int left, int right)
    {
        this.left = left;
        this.right = right;
        return (this.left < this.right)?this.left: this.right;
    }    
   
}
