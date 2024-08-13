import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ImpostoParceladoDetailComponent } from './imposto-parcelado-detail.component';

describe('ImpostoParcelado Management Detail Component', () => {
  let comp: ImpostoParceladoDetailComponent;
  let fixture: ComponentFixture<ImpostoParceladoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImpostoParceladoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ImpostoParceladoDetailComponent,
              resolve: { impostoParcelado: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ImpostoParceladoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImpostoParceladoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load impostoParcelado on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ImpostoParceladoDetailComponent);

      // THEN
      expect(instance.impostoParcelado()).toEqual(expect.objectContaining({ id: 123 }));
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
