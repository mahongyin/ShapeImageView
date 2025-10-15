package com.google.android.material;

import static com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap;
import static java.lang.Math.abs;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.edge.MyTriangleEdgeTreatment;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.shape.Shapeable;
import com.mhy.shape_imageview.R;

/**
 * Created By Mahongyin
 * Date    2025/10/11 16:22
 */
@SuppressLint("RestrictedApi")
public class MaterialShapeTextView extends AppCompatTextView implements Shapeable {
    private final ShapeAppearancePathProvider pathProvider = new ShapeAppearancePathProvider();
    private final RectF destination;
    private final RectF maskRect;
    private final Paint borderPaint;
    private final Paint clearPaint;
    private final Path path = new Path();

    private ColorStateList strokeColor;
    private ShapeAppearanceModel shapeAppearanceModel;
    @Dimension
    private float strokeWidth;
    private final Path maskPath;


    @Override
    protected void onDetachedFromWindow() {
        setLayerType(LAYER_TYPE_NONE, null);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setLayerType(LAYER_TYPE_HARDWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(maskPath, clearPaint);
        drawStroke(canvas);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        updateShapeMask(width, height);
    }


    @NonNull
    @Override
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return shapeAppearanceModel;
    }

    private void updateShapeMask(int width, int height) {
        destination.set(
                getPaddingLeft(),
                getPaddingTop(),
                width - getPaddingRight(),
                height - getPaddingBottom());
        pathProvider.calculatePath(shapeAppearanceModel, 1f /*interpolation*/, destination, path);
        // Remove path from rect to draw with clear paint.
        maskPath.rewind();
        maskPath.addPath(path);
        // Do not include padding to clip the background too.
        maskRect.set(0, 0, width, height);
        maskPath.addRect(maskRect, Path.Direction.CCW);
    }

    private void drawStroke(Canvas canvas) {
        if (strokeColor == null) {
            return;
        }

        borderPaint.setStrokeWidth(strokeWidth);
        int colorForState =
                strokeColor.getColorForState(getDrawableState(), strokeColor.getDefaultColor());

        if (strokeWidth > 0 && colorForState != Color.TRANSPARENT) {
            borderPaint.setColor(colorForState);
            canvas.drawPath(path, borderPaint);
        }
    }

    /**
     * Sets the stroke color resource for this ImageView. Both stroke color and stroke width must be
     * set for a stroke to be drawn.
     *
     * @param strokeColorResourceId Color resource to use for the stroke.
     * @attr ref com.google.android.material.R.styleable#ShapeableTextView_strokeColor
     * @see #setStrokeColor(ColorStateList)
     * @see #getStrokeColor()
     */
    public void setStrokeColorResource(@ColorRes int strokeColorResourceId) {
        setStrokeColor(AppCompatResources.getColorStateList(getContext(), strokeColorResourceId));
    }

    /**
     * Returns the stroke color for this ImageView.
     *
     * @attr ref com.google.android.material.R.styleable#ShapeableTextView_strokeColor
     * @see #setStrokeColor(ColorStateList)
     * @see #setStrokeColorResource(int)
     */
    @Nullable
    public ColorStateList getStrokeColor() {
        return strokeColor;
    }

    /**
     * Sets the stroke width for this ImageView. Both stroke color and stroke width must be set for a
     * stroke to be drawn.
     *
     * @param strokeWidth Stroke width for this ImageView.
     * @attr ref com.google.android.material.R.styleable#ShapeableTextView_strokeWidth
     * @see #setStrokeWidthResource(int)
     * @see #getStrokeWidth()
     */
    public void setStrokeWidth(@Dimension float strokeWidth) {
        if (this.strokeWidth != strokeWidth) {
            this.strokeWidth = strokeWidth;
            invalidate();
        }
    }

    /**
     * Sets the stroke width dimension resource for this ImageView. Both stroke color and stroke width
     * must be set for a stroke to be drawn.
     *
     * @param strokeWidthResourceId Stroke width dimension resource for this ImageView.
     * @attr ref com.google.android.material.R.styleable#ShapeableTextView_strokeWidth
     * @see #setStrokeWidth(float)
     * @see #getStrokeWidth()
     */
    public void setStrokeWidthResource(@DimenRes int strokeWidthResourceId) {
        setStrokeWidth(getResources().getDimensionPixelSize(strokeWidthResourceId));
    }


    /**
     * Gets the stroke width for this ImageView.
     *
     * @return Stroke width for this ImageView.
     * @attr ref com.google.android.material.R.styleable#ShapeableTextView_strokeWidth
     * @see #setStrokeWidth(float)
     * @see #setStrokeWidthResource(int)
     */
    @Dimension
    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeColor(@Nullable ColorStateList strokeColor) {
        this.strokeColor = strokeColor;
        invalidate();
    }


    public MaterialShapeTextView(@NonNull Context context) {
        this(context, null);
    }

    public MaterialShapeTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialShapeTextView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(wrap(context, attrs, defStyleAttr,
                com.google.android.material.R.style.Widget_MaterialComponents_TextView),
                attrs, defStyleAttr);
        context = getContext();
        clearPaint = new Paint();
        clearPaint.setAntiAlias(true);
        clearPaint.setColor(Color.WHITE);
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        destination = new RectF();
        maskRect = new RectF();
        maskPath = new Path();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialShapeTextView, defStyleAttr,
                com.google.android.material.R.style.Widget_MaterialComponents_TextView);
        try {
            strokeColor = getColorStateList(context, a, R.styleable.MaterialShapeTextView_strokeColor);

            strokeWidth = a.getDimensionPixelSize(R.styleable.MaterialShapeTextView_strokeWidth, 0);

            int shapeType =
                    a.getInt(R.styleable.MaterialShapeTextView_shapeText_Type, CornerFamily.ROUNDED);
            int shapeTypeTopLeft =
                    a.getInt(R.styleable.MaterialShapeTextView_shapeText_Type_TopLeft, shapeType);
            int shapeTypeTopRight =
                    a.getInt(R.styleable.MaterialShapeTextView_shapeText_Type_TopRight, shapeType);
            int shapeTypeBottomRight =
                    a.getInt(R.styleable.MaterialShapeTextView_shapeText_Type_BottomRight, shapeType);
            int shapeTypeBottomLeft =
                    a.getInt(R.styleable.MaterialShapeTextView_shapeText_Type_BottomLeft, shapeType);

            float allTriangleEdge =
                    a.getDimension(R.styleable.MaterialShapeTextView_shapeText_edge_AllTriangle, 0f);
            float topTriangleEdge =
                    a.getDimension(R.styleable.MaterialShapeTextView_shapeText_edge_TopTriangle, 0f);
            float leftTriangleEdge =
                    a.getDimension(R.styleable.MaterialShapeTextView_shapeText_edge_LeftTriangle, 0f);
            float rightTriangleEdge =
                    a.getDimension(R.styleable.MaterialShapeTextView_shapeText_edge_RightTriangle, 0f);
            float bottomTriangleEdge =
                    a.getDimension(R.styleable.MaterialShapeTextView_shapeText_edge_BottomTriangle, 0f);

            ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder();
            if (allTriangleEdge != 0f) {
                builder.setAllEdges(new MyTriangleEdgeTreatment(abs(allTriangleEdge), allTriangleEdge > 0));
            }
            if (topTriangleEdge != 0f) {
                builder.setTopEdge(new MyTriangleEdgeTreatment(abs(topTriangleEdge), topTriangleEdge > 0));
            }
            if (rightTriangleEdge != 0f) {builder.setRightEdge(new MyTriangleEdgeTreatment(abs(rightTriangleEdge), rightTriangleEdge > 0));
            }
            if (leftTriangleEdge != 0f) {
                builder.setLeftEdge(new MyTriangleEdgeTreatment(abs(leftTriangleEdge), leftTriangleEdge > 0));
            }
            if (bottomTriangleEdge != 0f) {
                builder.setBottomEdge(new MyTriangleEdgeTreatment(abs(bottomTriangleEdge), bottomTriangleEdge > 0));
            }
            if (a.hasValue(R.styleable.MaterialShapeTextView_shapeText_corner_All)) {
                CornerSize cornerSize = getCornerSize(
                        a, R.styleable.MaterialShapeTextView_shapeText_corner_All,
                        new AbsoluteCornerSize(0F)
                );
                builder.setAllCornerSizes(cornerSize);
            } else {
                CornerSize cornerSizeTopLeft =
                        getCornerSize(a, R.styleable.MaterialShapeTextView_shapeText_corner_TopLeft,
                                new AbsoluteCornerSize(0F));
                CornerSize cornerSizeTopRight =
                        getCornerSize(a, R.styleable.MaterialShapeTextView_shapeText_corner_TopRight,
                                new AbsoluteCornerSize(0F));
                CornerSize cornerSizeBottomRight =
                        getCornerSize(a, R.styleable.MaterialShapeTextView_shapeText_corner_BottomRight,
                                new AbsoluteCornerSize(0F));
                CornerSize cornerSizeBottomLeft =
                        getCornerSize(a, R.styleable.MaterialShapeTextView_shapeText_corner_BottomLeft,
                                new AbsoluteCornerSize(0F));
                builder.setTopLeftCorner(shapeTypeTopLeft, cornerSizeTopLeft)
                        .setTopRightCorner(shapeTypeTopRight, cornerSizeTopRight)
                        .setBottomRightCorner(shapeTypeBottomRight, cornerSizeBottomRight)
                        .setBottomLeftCorner(shapeTypeBottomLeft, cornerSizeBottomLeft);
            }
            borderPaint = new Paint();
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setAntiAlias(true);
            shapeAppearanceModel = builder.build();
        } finally {
            a.recycle();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new OutlineProvider());
        }
    }

    private ColorStateList getColorStateList(
            @NonNull Context context, @NonNull TypedArray attributes, @StyleableRes int index) {
        if (attributes.hasValue(index)) {
            int resourceId = attributes.getResourceId(index, 0);
            if (resourceId != 0) {
                ColorStateList value = AppCompatResources.getColorStateList(context, resourceId);
                if (value != null) {
                    return value;
                }
            }
        }

        // Reading a single color with getColorStateList() on API 15 and below doesn't always correctly
        // read the value. Instead we'll first try to read the color directly here.
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            int color = attributes.getColor(index, -1);
            if (color != -1) {
                return ColorStateList.valueOf(color);
            }
        }
        return attributes.getColorStateList(index);
    }
    private CornerSize getCornerSize(TypedArray a, int index, CornerSize defaultValue) {
        TypedValue value = a.peekValue(index);
        if (value == null) {
            return defaultValue;
        }
        if (value.type == TypedValue.TYPE_DIMENSION) {//TYPE_REFERENCE
            //最终我们可能希望将其更改为调用 getDimension()，因为角尺寸支持浮点数。
            return new AbsoluteCornerSize(
                    TypedValue.complexToDimension(value.data, a.getResources().getDisplayMetrics())
            );
        } else if (value.type == TypedValue.TYPE_FRACTION) {
            return new RelativeCornerSize(value.getFraction(1.0f, 1.0f));
        } else {
            return defaultValue;
        }
    }

    @Nullable
    private MaterialShapeDrawable shadowDrawable;

    @Override
    public void setShapeAppearanceModel(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.shapeAppearanceModel = shapeAppearanceModel;
        if (shadowDrawable != null) {
            shadowDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        }
        updateShapeMask(getWidth(), getHeight());
        invalidate();
        if (Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    class OutlineProvider extends ViewOutlineProvider {
        private final Rect rect = new Rect();
        @Override
        public void getOutline(View view, Outline outline) {
            if (shapeAppearanceModel == null) {
                return;
            }
            if (shadowDrawable == null) {
                shadowDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
            }
            destination.round(rect);
            shadowDrawable.setBounds(rect);
            shadowDrawable.getOutline(outline);
        }
    }



// old
//    @Override
//    public void setShapeAppearanceModel2(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
//        this.shapeAppearanceModel = shapeAppearanceModel;
//        updateShapeMask(getWidth(), getHeight());
//        invalidate();
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    class OutlineProvider2 extends ViewOutlineProvider {
//        private Rect rect = new Rect();
//        @Override
//        public void getOutline(View view, Outline outline) {
//            if (shapeAppearanceModel != null && isRoundRect(shapeAppearanceModel, destination)) {
//                destination.round(rect);
//                float cornerSize =
//                        shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(destination);
//                outline.setRoundRect(rect, cornerSize);
//            }
//        }
//        public boolean isRoundRect(ShapeAppearanceModel shapeAppearanceModel, @NonNull RectF bounds) {
//            EdgeTreatment leftEdge = shapeAppearanceModel.getLeftEdge();
//            EdgeTreatment rightEdge = shapeAppearanceModel.getRightEdge();
//            EdgeTreatment topEdge = shapeAppearanceModel.getTopEdge();
//            EdgeTreatment bottomEdge = shapeAppearanceModel.getBottomEdge();
//            CornerSize topLeftCornerSize = shapeAppearanceModel.getTopLeftCornerSize();
//            CornerSize topRightCornerSize = shapeAppearanceModel.getTopRightCornerSize();
//            CornerSize bottomLeftCornerSize = shapeAppearanceModel.getBottomLeftCornerSize();
//            CornerSize bottomRightCornerSize = shapeAppearanceModel.getBottomRightCornerSize();
//            CornerTreatment topRightCorner = shapeAppearanceModel.getTopRightCorner();
//            CornerTreatment topLeftCorner = shapeAppearanceModel.getTopLeftCorner();
//            CornerTreatment bottomRightCorner = shapeAppearanceModel.getBottomRightCorner();
//            CornerTreatment bottomLeftCorner = shapeAppearanceModel.getBottomLeftCorner();
//
//            boolean hasDefaultEdges =
//                    leftEdge.getClass().equals(EdgeTreatment.class)
//                            && rightEdge.getClass().equals(EdgeTreatment.class)
//                            && topEdge.getClass().equals(EdgeTreatment.class)
//                            && bottomEdge.getClass().equals(EdgeTreatment.class);
//
//            float cornerSize = topLeftCornerSize.getCornerSize(bounds);
//
//            boolean cornersHaveSameSize =
//                    topRightCornerSize.getCornerSize(bounds) == cornerSize
//                            && bottomLeftCornerSize.getCornerSize(bounds) == cornerSize
//                            && bottomRightCornerSize.getCornerSize(bounds) == cornerSize;
//
//            boolean hasRoundedCorners =
//                    topRightCorner instanceof RoundedCornerTreatment
//                            && topLeftCorner instanceof RoundedCornerTreatment
//                            && bottomRightCorner instanceof RoundedCornerTreatment
//                            && bottomLeftCorner instanceof RoundedCornerTreatment;
//
//            return hasDefaultEdges && cornersHaveSameSize && hasRoundedCorners;
//        }
//    }
}
