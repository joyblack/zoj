#include<iostream>
using namespace std;

char map[4][4];// 地图
int maxNum,n;

bool CanPut(int row, int col)
{//测试是否可以放置碉堡到row行col列处，因为位置是从小到大前进的，因此只需要测试比待测试点小的位置
int i;
//测试col列上是否有面对面的碉堡
for (i = row - 1; i >= 0; --i)
{
if (map[i][col] == 'O') return false;
if (map[i][col] == 'X') break;
}
//测试row行上是否有面对面的碉堡
for (i = col - 1; i >= 0; --i)
{
if (map[row][i] == 'O') return false;
if (map[row][i] == 'X') break;
}
return true;
}

void Solve(int k,int curNum)
{
int x,y;
if(k==n*n)
{//到最后一个了
if(curNum>maxNum)
{//保存当前遍历路径找到的最大值
maxNum=curNum;
return;
}
}
else
{
x=k/n;//行号
y=k%n;//列号
if((map[x][y]=='.')&&(CanPut(x,y)==true))
{//当前点是空白处，并且可以放置碉堡
map[x][y]='O';//放置碉堡
Solve(k+1,curNum+1);//递归进入下一个位置
map[x][y]='.';//回溯
}
//当前点不能放置或回溯回来
Solve(k+1,curNum);
}
}

int main()
{
int i,j;
while(cin>>n&&n!=0)
{
//输入地图
for(i=0;i<n;i++)
{
for(j=0;j<n;j++)
{
cin>>map[i][j];
}
}
maxNum=0;//最多可能放置的数目
//开始深搜,起点设置为左上角
Solve(0,0);
cout<<maxNum<<endl;
}
return 0;
}