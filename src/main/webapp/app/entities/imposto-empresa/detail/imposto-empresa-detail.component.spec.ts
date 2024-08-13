import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ImpostoEmpresaDetailComponent } from './imposto-empresa-detail.component';

describe('ImpostoEmpresa Management Detail Component', () => {
  let comp: ImpostoEmpresaDetailComponent;
  let fixture: ComponentFixture<ImpostoEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImpostoEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ImpostoEmpresaDetailComponent,
              resolve: { impostoEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ImpostoEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImpostoEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load impostoEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ImpostoEmpresaDetailComponent);

      // THEN
      expect(instance.impostoEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
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
