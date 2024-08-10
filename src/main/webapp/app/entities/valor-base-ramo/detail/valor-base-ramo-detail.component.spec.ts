import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ValorBaseRamoDetailComponent } from './valor-base-ramo-detail.component';

describe('ValorBaseRamo Management Detail Component', () => {
  let comp: ValorBaseRamoDetailComponent;
  let fixture: ComponentFixture<ValorBaseRamoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ValorBaseRamoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ValorBaseRamoDetailComponent,
              resolve: { valorBaseRamo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ValorBaseRamoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ValorBaseRamoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load valorBaseRamo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ValorBaseRamoDetailComponent);

      // THEN
      expect(instance.valorBaseRamo()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
