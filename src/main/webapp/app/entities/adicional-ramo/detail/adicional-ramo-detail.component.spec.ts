import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AdicionalRamoDetailComponent } from './adicional-ramo-detail.component';

describe('AdicionalRamo Management Detail Component', () => {
  let comp: AdicionalRamoDetailComponent;
  let fixture: ComponentFixture<AdicionalRamoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdicionalRamoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AdicionalRamoDetailComponent,
              resolve: { adicionalRamo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AdicionalRamoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdicionalRamoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adicionalRamo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AdicionalRamoDetailComponent);

      // THEN
      expect(instance.adicionalRamo()).toEqual(expect.objectContaining({ id: 123 }));
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
