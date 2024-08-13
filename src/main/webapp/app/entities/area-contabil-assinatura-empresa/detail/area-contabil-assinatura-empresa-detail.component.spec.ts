import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AreaContabilAssinaturaEmpresaDetailComponent } from './area-contabil-assinatura-empresa-detail.component';

describe('AreaContabilAssinaturaEmpresa Management Detail Component', () => {
  let comp: AreaContabilAssinaturaEmpresaDetailComponent;
  let fixture: ComponentFixture<AreaContabilAssinaturaEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AreaContabilAssinaturaEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AreaContabilAssinaturaEmpresaDetailComponent,
              resolve: { areaContabilAssinaturaEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AreaContabilAssinaturaEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AreaContabilAssinaturaEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load areaContabilAssinaturaEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AreaContabilAssinaturaEmpresaDetailComponent);

      // THEN
      expect(instance.areaContabilAssinaturaEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
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
