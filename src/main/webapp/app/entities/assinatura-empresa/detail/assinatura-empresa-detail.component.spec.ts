import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AssinaturaEmpresaDetailComponent } from './assinatura-empresa-detail.component';

describe('AssinaturaEmpresa Management Detail Component', () => {
  let comp: AssinaturaEmpresaDetailComponent;
  let fixture: ComponentFixture<AssinaturaEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AssinaturaEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AssinaturaEmpresaDetailComponent,
              resolve: { assinaturaEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AssinaturaEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssinaturaEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load assinaturaEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AssinaturaEmpresaDetailComponent);

      // THEN
      expect(instance.assinaturaEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
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
