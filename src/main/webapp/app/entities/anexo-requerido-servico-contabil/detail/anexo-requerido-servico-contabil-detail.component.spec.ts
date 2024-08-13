import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AnexoRequeridoServicoContabilDetailComponent } from './anexo-requerido-servico-contabil-detail.component';

describe('AnexoRequeridoServicoContabil Management Detail Component', () => {
  let comp: AnexoRequeridoServicoContabilDetailComponent;
  let fixture: ComponentFixture<AnexoRequeridoServicoContabilDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnexoRequeridoServicoContabilDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AnexoRequeridoServicoContabilDetailComponent,
              resolve: { anexoRequeridoServicoContabil: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AnexoRequeridoServicoContabilDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnexoRequeridoServicoContabilDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load anexoRequeridoServicoContabil on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AnexoRequeridoServicoContabilDetailComponent);

      // THEN
      expect(instance.anexoRequeridoServicoContabil()).toEqual(expect.objectContaining({ id: 123 }));
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
