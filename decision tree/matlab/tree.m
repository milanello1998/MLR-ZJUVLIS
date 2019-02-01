
IM=csvread('GC_in.csv',1,0);
nAttr=size(IM,2)-1;
nSample=size(IM,1);                    %139888;155803

X=IM(:,1:nAttr);
Y=IM(:,nAttr+1);

tree=fitctree(X,Y,'MinParent',1,'MaxCat',20,'CategoricalPredictors','all');
pred1=predict(tree, X);
view(tree,'Mode', 'Graph');

data = csvread('GC_in.csv',1,0);
XX=IM(:,1:nAttr);
YY=IM(:,nAttr+1);
pred=predict(tree, XX);

res = YY - pred;
[m n] = size(res);
err = 0;
for i = 1:m
    if(res(i) ~= 0)
        err = err + 1;
    end
end
errRate = err / size(YY,1)

%view
n = size(tree.IsBranchNode,1);
fileID = fopen('TreeNode.txt','w');
for i = 1 : n    
    fprintf(fileID,'%s,%d,%d,',tree.NodeClass{i},tree.Children(i,1),tree.Children(i,2));
    fprintf(fileID,'%s,',tree.CutPredictor{i});
    fprintf(fileID,'%d ',tree.CutCategories{i,1});
    fprintf(fileID,',');
    fprintf(fileID,'%d ',tree.CutCategories{i,2});
    fprintf(fileID,'\n');
end
fclose(fileID);

