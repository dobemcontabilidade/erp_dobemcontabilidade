import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ParcelamentoImpostoDetailComponent } from './parcelamento-imposto-detail.component';

describe('ParcelamentoImposto Management Detail Component', () => {
  let comp: ParcelamentoImpostoDetailComponent;
  let fixture: ComponentFixture<ParcelamentoImpostoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ParcelamentoImpostoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ParcelamentoImpostoDetailComponent,
              resolve: { parcelamentoImposto: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ParcelamentoImpostoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParcelamentoImpostoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load parcelamentoImposto on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ParcelamentoImpostoDetailComponent);

      // THEN
      expect(instance.parcelamentoImposto()).toEqual(expect.objectContaining({ id: 123 }));
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
