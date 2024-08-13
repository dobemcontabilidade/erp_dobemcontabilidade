import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AreaContabilDetailComponent } from './area-contabil-detail.component';

describe('AreaContabil Management Detail Component', () => {
  let comp: AreaContabilDetailComponent;
  let fixture: ComponentFixture<AreaContabilDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AreaContabilDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AreaContabilDetailComponent,
              resolve: { areaContabil: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AreaContabilDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AreaContabilDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load areaContabil on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AreaContabilDetailComponent);

      // THEN
      expect(instance.areaContabil()).toEqual(expect.objectContaining({ id: 123 }));
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
