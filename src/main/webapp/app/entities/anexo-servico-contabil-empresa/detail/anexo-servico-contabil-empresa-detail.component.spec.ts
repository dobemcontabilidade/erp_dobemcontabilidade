import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AnexoServicoContabilEmpresaDetailComponent } from './anexo-servico-contabil-empresa-detail.component';

describe('AnexoServicoContabilEmpresa Management Detail Component', () => {
  let comp: AnexoServicoContabilEmpresaDetailComponent;
  let fixture: ComponentFixture<AnexoServicoContabilEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnexoServicoContabilEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AnexoServicoContabilEmpresaDetailComponent,
              resolve: { anexoServicoContabilEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AnexoServicoContabilEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnexoServicoContabilEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load anexoServicoContabilEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AnexoServicoContabilEmpresaDetailComponent);

      // THEN
      expect(instance.anexoServicoContabilEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
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
