package map;

import vector2d.Vector2d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class MatingAssistant {
  private final Map<Vector2d, Field> fields;
  private Vector2d parentPosition;
  private final int day;
  private final Vector2d leftBottomCorner = new Vector2d(0,0);
  private final Vector2d rightTopCorner;
  private final WorldMap map;

  public MatingAssistant(Map<Vector2d, Field> fields, int day, Vector2d rightTopCorner, WorldMap map)
  {
    this.fields = fields;
    this.day=day;
    this.rightTopCorner = rightTopCorner;
    this.map = map;
  }

  public void startMating()
  {
    Collection<Field> colOfFields = fields.values();
    ArrayList<Field> fields = new ArrayList<>(colOfFields);
    for (Field current : fields)
    {
      if (current.containsAnimals())
      {
        parentPosition = current.getPosition();
        current.mate(day, positionOfNewbornAnimal());
      }
    }
  }

  private Vector2d positionOfNewbornAnimal()
  {
    ArrayList<Vector2d> positions = freeFieldsAroundParentPosition();
    if(positions.size() > 0)
    {
      return positions.get((int)(Math.random() * positions.size()));
    }
    else
    {
      int i = (int)(Math.random() * 3) - 1;
      int j = (int)(Math.random() * 3) - 1;
      return(map.adjustPosition(new Vector2d(parentPosition.x + i, parentPosition.y+j)));
    }
  }

  private ArrayList<Vector2d> freeFieldsAroundParentPosition()
  {
    ArrayList<Vector2d> freeFields = new ArrayList<>();
    for(int i = -1;i<2;i++)
    {
      for(int j =-1;j<2;j++)
      {
        Vector2d tmp = new Vector2d(parentPosition.x + i, parentPosition.y + j);
        tmp = map.adjustPosition(tmp);
        if(!fields.containsKey(tmp))
        {
          freeFields.add(tmp);
        }
        else
        {
          if(!fields.get(tmp).containsAnimals())
          {
            freeFields.add(tmp);
          }
        }
      }
    }
    return freeFields;
  }
}
