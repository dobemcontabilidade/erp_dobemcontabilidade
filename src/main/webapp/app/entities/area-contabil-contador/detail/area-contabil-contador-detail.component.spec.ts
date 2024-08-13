import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AreaContabilContadorDetailComponent } from './area-contabil-contador-detail.component';

describe('AreaContabilContador Management Detail Component', () => {
  let comp: AreaContabilContadorDetailComponent;
  let fixture: ComponentFixture<AreaContabilContadorDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AreaContabilContadorDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AreaContabilContadorDetailComponent,
              resolve: { areaContabilContador: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AreaContabilContadorDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AreaContabilContadorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load areaContabilContador on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AreaContabilContadorDetailComponent);

      // THEN
      expect(instance.areaContabilContador()).toEqual(expect.objectContaining({ id: 123 }));
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
